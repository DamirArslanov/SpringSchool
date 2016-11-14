package school.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import school.dao.interfaces.ScheduleDao;
import school.entity.Schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ArslanovDamir on 26.09.2016.
 */
@Repository
public class ScheduleDaoImpl implements ScheduleDao {



    public SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void addSchedule(Schedule schedule) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(schedule);
    }

    @Override
    public void updateSchedule(Schedule schedule) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(schedule);
    }

    @Override
    public void removeSchedule(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Schedule schedule = (Schedule) session.load(Schedule.class, new Integer(id));
        if (schedule != null) {
            session.delete(schedule);
        }
    }

    @Override
    public Schedule getScheduleById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Schedule schedule = (Schedule) session.load(Schedule.class, new Integer(id));
        return schedule;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Schedule> listSchedules() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Schedule> listSchedules = session.createQuery("FROM Schedule").list();
        return listSchedules;
    }


    //Поиск уроков на неделю для определенного класса
    @Override
    public List<Schedule> listScheduleClass(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        List<Schedule> listScheduleClass = new ArrayList<>();
        String hql = "FROM Schedule schedule WHERE schedule.schoolClass.class_id = (:id) ORDER BY schedule.weekday.id";
        Query query = session.createQuery(hql).setParameter("id", id);
        if (!query.list().isEmpty()) {
            listScheduleClass = query.list();
            return listScheduleClass;
        }
        return listScheduleClass;
    }

    //Поиск уроков на неделю для определенного учителя
    @Override
    public List<Schedule> listScheduleTeacher(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        String hql = "FROM Schedule schedule WHERE schedule.teacher.t_id = (:id)";
        Query query = session.createQuery(hql).setParameter("id", id);
        List<Schedule> listScheduleTeacher = query.list();
        return listScheduleTeacher;
    }

    @Override
    public List<Schedule> listSelectedShedule(int schedule, int teacher) {
        Session session = this.sessionFactory.getCurrentSession();
        if (schedule == 000) {
            String hqlClassNULL = "FROM Schedule schedule WHERE schedule.teacher.t_id = (:teacher)";
            Query queryClassNULL = session.createQuery(hqlClassNULL).setParameter("teacher", teacher);
            List<Schedule> listSelectedShedule = queryClassNULL.list();
            return listSelectedShedule;
        }else if (teacher == 000) {
            String hqlTeacherNULL = "FROM Schedule schedule WHERE schedule.schoolClass.class_id = (:schedule)";
            Query queryTeacherNULL = session.createQuery(hqlTeacherNULL).setParameter("schedule", schedule);
            List<Schedule> listSelectedShedule = queryTeacherNULL.list();
            return listSelectedShedule;
        }else {
            String hql = "FROM Schedule schedule WHERE schedule.teacher.t_id = (:teacher) AND schedule.schoolClass.class_id = (:schedule)";
            Query query = session.createQuery(hql).setParameter("teacher", teacher).setParameter("schedule", schedule);
            List<Schedule> listSelectedShedule = query.list();
            return listSelectedShedule;
        }
    }
}
