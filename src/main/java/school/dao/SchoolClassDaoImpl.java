package school.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import school.dao.interfaces.SchoolClassDao;
import school.entity.SchoolClass;

import java.util.List;

/**
 * Created by ArslanovDamir on 19.09.2016.
 */
@Repository
public class SchoolClassDaoImpl implements SchoolClassDao {


    private SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void addSchoolClass(SchoolClass schoolClass) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(schoolClass);
    }

    public void updateSchoolClass(SchoolClass schoolClass) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(schoolClass);
    }

    public void removeSchoolClass(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        SchoolClass schoolClass = (SchoolClass) session.load(SchoolClass.class, new Integer(id));
        if (schoolClass != null) {
            session.delete(schoolClass);
        }
    }

    public SchoolClass getSchoolClassById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        SchoolClass schoolClass = (SchoolClass) session.load(SchoolClass.class, new Integer(id));
        return schoolClass;
    }

    public List<SchoolClass> listSchoolClass() {
        Session session = this.sessionFactory.getCurrentSession();
        List<SchoolClass> schoolClassList = session.createQuery("FROM SchoolClass").list();
        return schoolClassList;
    }
}
