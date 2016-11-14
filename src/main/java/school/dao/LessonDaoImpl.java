package school.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import school.dao.interfaces.LessonDao;
import school.dao.interfaces.ScheduleDao;
import school.entity.Lesson;
import school.entity.Schedule;
import school.entity.Teacher;
import school.utils.DateUtil;


import javax.persistence.NoResultException;
import java.util.*;

/**
 * Created by Cheshire on 01.10.2016.
 */
@Repository
public class LessonDaoImpl implements LessonDao {


    ScheduleDao scheduleDao;
    @Autowired
    public void setScheduleDao(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }


    private DateUtil dateUtil;
    @Autowired
    public void setDateUtil(DateUtil dateUtil) {
        this.dateUtil = dateUtil;
    }


    private SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addLesson(Lesson lesson) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(lesson);
    }

    @Override
    public void updateLesson(Lesson lesson) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(lesson);
    }

    @Override
    public void removeLesson(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Lesson lesson = (Lesson) session.load(Lesson.class, new Integer(id));
        if (lesson != null) {
            session.delete(lesson);
        }
    }

    @Override
    public Lesson getLessonById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Lesson lesson = (Lesson) session.load(Lesson.class, new Integer(id));
        return lesson;
    }

    @Override
    public List<Lesson> listLessons() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Lesson> lessonList = session.createQuery("FROM Lesson").list();
        return lessonList;
    }

    @Override
    public void createLessonBySchedule(Schedule schedule) {
        Session session = this.sessionFactory.getCurrentSession();
        Date date = new Date();
        Lesson lesson = new Lesson();
        String hql2 = "FROM Lesson lesson WHERE lesson.less_Date = (:date) AND lesson.schedule.id = (:scheduleid)";
        Query query = session.createQuery(hql2).setParameter("date", date).setParameter("scheduleid", schedule.getShed_id());
        if (!query.list().isEmpty()) {
            try {
                lesson = (Lesson) query.getSingleResult();
                System.out.println("Содержимое Query: " + query.list());
            }catch (NoResultException e) {
                System.out.println("Ошибка каста Lesson из Query. Содержимое Query: " + query.list().isEmpty());
            }
        } else {
            lesson.setLess_Date(new Date());
            lesson.setSchedule(schedule);
            session.persist(lesson);
        }
    }




    @Override
    public List<Lesson> getLessonsByTeacher(String teacherUsername) {

        List<Date> dates = dateUtil.giveMeThisWeekDays();
        Date startDate = dates.get(0);
        Date endDate = dates.get(dates.size() - 1);
        Session session = this.sessionFactory.getCurrentSession();
        String hql = "FROM Lesson lesson WHERE lesson.schedule.teacher.username = (:teacherUsername) AND  lesson.less_Date BETWEEN (:startDate) AND (:endDate)";
        Query query = session.createQuery(hql).setParameter("teacherUsername", teacherUsername).setParameter("startDate", startDate).setParameter("endDate", endDate);
        List<Lesson> lessons = query.list();
        return lessons;
    }


    @Override
    public void createWeekLessons() {
        Session session = this.sessionFactory.getCurrentSession();

        //Метод возвращает массив с датами нынешней или следующей недели
        //В зависимости от того, когда был вызван, т.к. он использует метод getWeekNumber()
        List<Date> dates = this.dateUtil.giveMeWeekDays();

        Date startDate = dates.get(0);
        Date endDate = dates.get(dates.size() - 1);

        //Получаем все объекты - расписание
        String allScheduleListRequest = "FROM Schedule";
        Query allScheduleQuery = session.createQuery(allScheduleListRequest);
        List<Schedule> allScheduleList = allScheduleQuery.list();

        //Получаем УЖЕ "ИСПОЛЬЗОВАННЫЕ" НА ЭТОЙ НЕДЕЛЕ объекты - расписание (попытка избежать дублирования)
        String allUsedScheduleRequest = "SELECT lesson.schedule FROM Lesson lesson WHERE lesson.less_Date BETWEEN (:startDate) AND (:endDate)";
        Query allUsedScheduleQuery = session.createQuery(allUsedScheduleRequest).setParameter("startDate", startDate).setParameter("endDate", endDate);
        List<Schedule> allUsedScheduleList = allUsedScheduleQuery.list();

        //Если список "ИСПОЛЬЗОВАННЫХ" объектов - расписание НЕ ПУСТ, то "вычищаем" им первый список (попытка избежать дублирования)
        if (!allUsedScheduleList.isEmpty()) {
           allScheduleList.removeAll(allUsedScheduleList);
        }
        Set<Schedule> scheduleSet = new LinkedHashSet<>();
        for (Schedule schedule : allScheduleList) {
            scheduleSet.add(schedule);
        }

        //И само создание уроков-лекции из расписания и даты требуемой недели
        for (Date date : dates) {
            int dayNumber = this.dateUtil.giveMeWeekNumberOnDate(date);
            for (Schedule schedule : scheduleSet) {
                if (schedule.getWeekday().getWeek_id() == dayNumber){
                    Lesson lesson = new Lesson();
                    lesson.setSchedule(schedule);
                    lesson.setLess_Date(this.dateUtil.setLessonTime(date, schedule));
                    session.persist(lesson);
                }
            }
        }
    }


    //Для создания или обновления лекции НА ЭТОЙ НЕДЕЛЕ У ДАННОГО КЛАССА
    @Override
    public void createWeekLessonsByClass(int classID, boolean future) {

        Session session = this.sessionFactory.getCurrentSession();
        List<Date> dates;
        if (future == true) {
            dates = dateUtil.giveMeFutureWeekDays();
        } else {
            dates = dateUtil.giveMeThisWeekDays();
        }
        Date startDate = dates.get(0);
        Date endDate = dates.get(dates.size() - 1);
        //Найдем все объекты-расписания по классу
        String request = "FROM Schedule schedule WHERE schedule.schoolClass.class_id = (:classID)";
        Query query = session.createQuery(request).setParameter("classID", classID);
        List<Schedule> allScheduleList = query.list();


        //Найдем все уроки по этому расписанию
        String request1 = "FROM Lesson lesson WHERE lesson.schedule.schoolClass.class_id = (:classID) AND lesson.less_Date BETWEEN (:startDate) AND (:endDate)";
        Query queryLesson = session.createQuery(request1)
                .setParameter("classID", classID)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate);
        List<Lesson> lessonList = queryLesson.list();

        Set<Schedule> used = new LinkedHashSet<>();
        // Обновим уже существующие лекции
        for (Schedule schedule : allScheduleList) {
            for (Lesson lesson : lessonList) {
                if (lesson.getSchedule().getShed_id() == schedule.getShed_id()) {
                    lesson.setSchedule(schedule);
                    if (future == true) {
                        lesson.setLess_Date(this.dateUtil.setLessonTime(dateUtil.getFutureDateByWeekNumber(schedule.getWeekday().getWeek_id()), schedule));
                    } else {
                        lesson.setLess_Date(this.dateUtil.setLessonTime(dateUtil.getDateByWeekNumber(schedule.getWeekday().getWeek_id()), schedule));
                    }
                    session.update(lesson);
                    used.add(schedule);
                }
            }
        }
        // Ранее собрали ИСПОЛЬЗОВАННЫЕ Schedule
        // - удалим их - найдем оставшиеся - создадим уроки
        if (!used.isEmpty()) {
            allScheduleList.removeAll(used);
        }

        for (Date date : dates) {
            int dayNumber = this.dateUtil.giveMeWeekNumberOnDate(date);
            for (Schedule schedule : allScheduleList) {
                if (schedule.getWeekday().getWeek_id() == dayNumber){
                    Lesson lesson = new Lesson();
                    lesson.setSchedule(schedule);
                    lesson.setLess_Date(this.dateUtil.setLessonTime(date, schedule));
                    session.persist(lesson);
                }
            }
        }

    }

    @Override
    public List<Lesson> getSelectedMonthLessons(Teacher teacher, Date startDate, Date endDate) {
        Session session = this.sessionFactory.getCurrentSession();
        List<Lesson> lessons = new ArrayList<>();
        int teacherID = teacher.getT_id();
        String hql = "FROM Lesson lesson WHERE lesson.schedule.teacher.t_id = (:teacherID) AND  lesson.less_Date BETWEEN (:startDate) AND (:endDate)";
        Query query = session.createQuery(hql).setParameter("teacherID", teacherID).setParameter("startDate", startDate).setParameter("endDate", endDate);
        if (!query.list().isEmpty()){
            lessons = query.list();
        }
        return lessons;
    }
}
