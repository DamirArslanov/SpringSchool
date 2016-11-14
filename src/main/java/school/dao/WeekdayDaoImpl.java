package school.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import school.dao.interfaces.WeekdayDao;
import school.entity.Weekday;

import java.util.List;

/**
 * Created by Cheshire on 27.09.2016.
 */
@Repository
public class WeekdayDaoImpl implements WeekdayDao {


    public SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }



    @Override
    public Weekday getWeekdayById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Weekday weekday = (Weekday) session.load(Weekday.class, new Integer(id));
        return weekday;
     }

    @Override
    public List<Weekday> listWeekdays() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Weekday> weekdays = session.createQuery("FROM Weekday").list();
        return weekdays;
    }
}
