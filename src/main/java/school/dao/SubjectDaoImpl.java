package school.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import school.dao.interfaces.SubjectDao;
import school.entity.Subject;

import java.util.List;

/**
 * Created by ArslanovDamir on 26.09.2016.
 */
@Repository
public class SubjectDaoImpl implements SubjectDao {

    public SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void addSubject(Subject subject) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(subject);
    }

    @Override
    public void updateSubject(Subject subject) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(subject);
    }

    @Override
    public void removeSubject(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Subject subject = (Subject) session.load(Subject.class, new Integer(id));
        if (subject != null) {
            session.delete(subject);
        }
    }

    @Override
    public Subject getSubjectById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Subject subject = (Subject) session.load(Subject.class, new Integer(id));
        return subject;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Subject> listSubjects() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Subject> listSubjects = session.createQuery("FROM Subject ").list();
        return listSubjects;
    }
}
