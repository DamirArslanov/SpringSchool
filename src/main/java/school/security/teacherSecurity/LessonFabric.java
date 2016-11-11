package school.security.teacherSecurity;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Cheshire on 04.10.2016.
 */
//@Component
////@Transactional
//public class LessonFabric {
//
////    @Autowired
////    ScheduleDao scheduleDao;
////
////    public void setScheduleDao(ScheduleDao scheduleDao) {
////        this.scheduleDao = scheduleDao;
////    }
////
////    @Autowired
////    private DateUtil dateUtil;
////
////    public void setDateUtil(DateUtil dateUtil) {
////        this.dateUtil = dateUtil;
////    }
////
////    @Autowired
////    private SessionFactory sessionFactory;
////
////    public void setSessionFactory(SessionFactory sessionFactory) {
////        this.sessionFactory = sessionFactory;
////    }
////
////
////
////
////
////
////    @Scheduled(cron="0 54 0 ? * 1-7")
////    public void createLessonZ() {
////        Session session = this.sessionFactory.getCurrentSession();
////        Date date = new Date();
////        int weekday = this.dateUtil.getWeekNumber();
////
////        String hql = "FROM Schedule schedule WHERE schedule.weekday.id =(:weekday)";
////        Query query = session.createQuery(hql).setParameter("weekday", weekday);
////        List<Schedule> scheduleList = query.list();
////
////
////        String hql2 = "SELECT lesson.schedule FROM Lesson lesson WHERE lesson.less_Date =(:date)";
////        Query query2 = session.createQuery(hql2).setParameter("date", date);
////        List<Schedule> usedScheduleList = query2.list();
////
////        scheduleList.removeAll(usedScheduleList);
////
////        for (Schedule schedule : scheduleList) {
////
////            Lesson lesson = new Lesson();
////            lesson.setSchedule(schedule);
////            lesson.setLess_Date(new Date());
////            session.persist(lesson);
////        }
////
////    }
//
//    @Scheduled(fixedRate=10000)
////    @Scheduled(cron="0 42 3 ? * 1-7")
//    public void sout() {
//        System.out.println("****************ФАБРИКА РАСПЕЧАТКИ ОТКРЫТА*************************");
//        System.nanoTime();
//        System.out.println("****************ФАБРИКА РАСПЕЧАТКИ ЗАКРЫТА*************************");
//    }
//
//}
