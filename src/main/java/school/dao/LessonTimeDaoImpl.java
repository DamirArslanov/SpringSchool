package school.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import school.dao.interfaces.LessonTimeDao;
import school.entity.Lesson;
import school.entity.LessonTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ArslanovDamir on 07.11.2016.
 */
@Repository
public class LessonTimeDaoImpl implements LessonTimeDao {


    private SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addTime(LessonTime lessonTime) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(lessonTime);
    }

    @Override
    public void deleteTime(int timeID) {
        Session session = this.sessionFactory.getCurrentSession();
        LessonTime lessonTime = (LessonTime) session.load(LessonTime.class, new Integer(timeID));
        if (lessonTime != null) {
            session.delete(lessonTime);
        }

    }

    @Override
    public LessonTime getTimeByValue(String timeValue) {
        Session session = this.sessionFactory.getCurrentSession();
        LessonTime lessonTime;
        String hql = "FROM LessonTime lessontime WHERE lessontime.time = (:timeValue)";
        Query query = session.createQuery(hql).setParameter("timeValue", timeValue);
        if (!query.list().isEmpty()) {
            try {
                lessonTime = (LessonTime) query.getSingleResult();
            } catch (Exception e) {
                lessonTime = (LessonTime) query.list().get(0);
                System.out.println("Вошли в catch => значит пришел массив данных, когда-то произошло дублирование LessonTime");
            }
            return lessonTime;
        } else return null;
    }

    @Override
    public List<LessonTime> lessonTimeList() {
        Session session = this.sessionFactory.getCurrentSession();
        List<LessonTime> lessonTimeList = new ArrayList<>();
        String hql = "FROM LessonTime";
        Query query = session.createQuery(hql);
        if (!query.list().isEmpty()) {
            lessonTimeList =  query.list();
            return lessonTimeList;
        } else {
            return lessonTimeList;
        }
    }
}
