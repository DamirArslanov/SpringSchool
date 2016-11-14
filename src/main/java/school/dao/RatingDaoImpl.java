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

import java.util.*;

/**
 * Created by ArslanovDamir on 28.09.2016.
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
    public List<Rating> getRatingsByChildren(int childrenID, Date date) {
        Session session = this.sessionFactory.getCurrentSession();
        List<Date> dates = new LinkedList<>();
        dates = dateUtil.getFirstAndLastDaysOfSelectedMonth(date);
        Date startDate = dates.get(0);
        Date endDate = dates.get(dates.size() - 1);
        List<Rating> ratings = new ArrayList<>();
        String hql = "FROM Rating rating WHERE rating.children.ch_id = (:childrenID)AND rating.rt_Date BETWEEN (:startDate) AND (:endDate) ORDER BY rating.subject.sub_name ASC, rating.rt_Date ASC";
        Query query = session.createQuery(hql)
                .setParameter("childrenID", childrenID)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate);
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
        List<Rating> ratingsByLesson = new LinkedList<>();
        String hql = "SELECT lesson.ratingList FROM Lesson lesson WHERE lesson.less_id = (:lesson_id)";
        Query query = session.createQuery(hql).setParameter("lesson_id", lesson_id);
        if (!query.list().isEmpty()) {
            ratingsByLesson = query.list();
            return ratingsByLesson;
        }
        return  ratingsByLesson;
    }

    @Override
    public void addRatingWithMap(Map<Integer, String>ratingMap, Lesson lesson) {
        Session session = this.sessionFactory.getCurrentSession();

        for (Map.Entry<Integer, String> entry : ratingMap.entrySet()) {
            Rating rating = new Rating();
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
            rating.setRt_Date(lesson.getLess_Date());
            Schedule schedule = this.scheduleDao.getScheduleById(lesson.getSchedule().getShed_id());
            rating.setSubject(schedule.getSubject());
            session.persist(rating);
        }
    }

    @Override
    public List<Rating> getMonthRating(Date startDate, Date endDate, Children children, Subject subject) {
        List<Rating> ratings = new LinkedList<>();
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
        }
        return ratings;

    }
}
