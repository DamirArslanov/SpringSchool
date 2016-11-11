package school.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import school.dao.interfaces.ChildrenDao;
import school.dao.interfaces.RatingDao;
import school.dao.interfaces.ScheduleDao;
import school.dao.interfaces.SubjectDao;
import school.entity.*;
import school.utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Cheshire on 28.09.2016.
 */
@Repository
public class RatingDaoImpl implements RatingDao {



    public SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    private ChildrenDao childrenDao;

    @Autowired
    public void setChildrenDao(ChildrenDao childrenDao) {
        this.childrenDao = childrenDao;
    }


    ScheduleDao scheduleDao;

    @Autowired
    public void setScheduleDao(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }


    private SubjectDao subjectDao;

    @Autowired
    public void setSubjectDao(SubjectDao subjectDao) {
        this.subjectDao = subjectDao;
    }

    DateUtil dateUtil;

    @Autowired
    public void setDateUtil(DateUtil dateUtil) {
        this.dateUtil = dateUtil;
    }

    @Override
    public void addRating(Rating rating) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(rating);
    }

    @Override
    public void updateRating(Rating rating) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(rating);
    }

    @Override
    public void removeRating(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Rating rating = (Rating) session.load(Rating.class, new Integer(id));
        if (rating != null) {
            session.delete(rating);
        }
    }

    @Override
    public Rating getRatingById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Rating rating = (Rating) session.load(Rating.class, new Integer(id));
        return rating;
    }

    @Override
    public List<Rating> getRatingsByChildren(int childrenID) {
        Session session = this.sessionFactory.getCurrentSession();
        List<Rating> ratings = new ArrayList<>();
        String hql = "FROM Rating rating WHERE rating.children.ch_id = (:childrenID) ORDER BY rating.subject.sub_name ASC";
        Query query = session.createQuery(hql).setParameter("childrenID", childrenID);
        if (!query.list().isEmpty()) {
            ratings = query.list();
            return ratings;
        }
        return ratings;
    }



    @Override
    public List<Rating> listRatings() {
        System.out.println("****************ФАБРИКА ОЦЕНОК ОТКРЫТА*************************");
        Session session = this.sessionFactory.getCurrentSession();
        List<Rating> listRatings = session.createQuery("FROM Rating").list();
        System.out.println("****************ФАБРИКА ЗАКРЫТА*************************");
        return listRatings;
    }

    @Override
    public List<Rating> getRatingsByLesson(int lesson_id) {
        Session session = this.sessionFactory.getCurrentSession();
        List<Rating> ratingsByLesson;
        String hql = "SELECT lesson.ratingList FROM Lesson lesson WHERE lesson.less_id = (:lesson_id)";
        Query query = session.createQuery(hql).setParameter("lesson_id", lesson_id);
        if (!query.list().isEmpty()) {
            ratingsByLesson = query.list();
            return ratingsByLesson;
        } else return null;
    }

    @Override
    public void addRatingWithMap(Map<Integer, String>ratingMap, Lesson lesson) {
        Session session = this.sessionFactory.getCurrentSession();

        for (Map.Entry<Integer, String> entry : ratingMap.entrySet()) {
            Rating rating = new Rating();
            System.out.println("ФАБРИКА ОЦЕНОК ОТКРЫТА! СЕЙЧАС НАЧНЕМ!");
            rating.setChildren(this.childrenDao.getChildrenById(entry.getKey()));
            //Попытка автоматического прописывания оценки "Присутствовал" по умолчанию
            if (entry.getValue() != null) {
                if (entry.getValue().equalsIgnoreCase("н")) {
                    rating.setEvaluation(0);
                } else if (entry.getValue().equalsIgnoreCase("п")) {
                    rating.setEvaluation(1);
                } else { //в любом другом случае ПЫТАЕМСЯ получить INTEGER, если ошибка - присвоим ПРИСУТСТВОВАЛ
                    try {
                        rating.setEvaluation(Integer.parseInt(entry.getValue()));
                    } catch (Exception e) {
                        rating.setEvaluation(1);
                    }
                }
            } else {
                rating.setEvaluation(1);
            }
            rating.setLesson(lesson);
            System.out.println("ПРИСВОИЛИ ОЦЕНКЕ ЛЕКЦИЮ! " + lesson.getLess_id());
            rating.setRt_Date(lesson.getLess_Date());
            System.out.println("ПРИСВОИЛИ ОЦЕНКЕ ДАТУ! " + lesson.getLess_Date());
            //Пока не ясно почему не работает напрямую через гет
            Schedule schedule = this.scheduleDao.getScheduleById(lesson.getSchedule().getShed_id());
            rating.setSubject(schedule.getSubject());
            System.out.println("ПРИСВОИЛИ ОЦЕНКЕ ПРЕДМЕТ! " + schedule.getSubject().getSub_name());
            session.persist(rating);
            System.out.println("Добавлена оценка: " + rating.getEvaluation());
        }

    }

    @Override
    public List<Rating> getMonthRating(Date startDate, Date endDate, Children children, Subject subject) {
        List<Rating> ratings = new ArrayList<>();
        Session session = this.sessionFactory.getCurrentSession();
        String ratingsSelectedMonth = "FROM Rating rating WHERE rating.children.ch_id = (:children) and rating.subject.sub_id = (:subject) and rating.rt_Date BETWEEN (:startDate) AND (:endDate) ORDER BY rating.rt_Date";
        Query ratingsSelectedMonthQuery = session.createQuery(ratingsSelectedMonth)
                .setParameter("children", children.getCh_id())
                .setParameter("subject", subject.getSub_id())
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate);
        if (!ratingsSelectedMonthQuery.list().isEmpty()) {
            ratings = ratingsSelectedMonthQuery.list();
            return ratings;
        }else return null;

    }
}
