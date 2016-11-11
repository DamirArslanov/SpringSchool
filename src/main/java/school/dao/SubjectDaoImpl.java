package school.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import school.dao.interfaces.SubjectDao;
import school.entity.Subject;

import java.util.List;

/**
 * Created by Cheshire on 26.09.2016.
 */
@Repository
public class SubjectDaoImpl implements SubjectDao {

    @Autowired
    public SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }





    @Override
    public void addSubject(Subject subject) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(subject);
        System.out.println("Предмет добавлен. Детали предмета: " + subject);
    }

    @Override
    public void updateSubject(Subject subject) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(subject);
        System.out.println("Предмет обновлен. Детали предмета: " + subject);
    }

    @Override
    public void removeSubject(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Subject subject = (Subject) session.load(Subject.class, new Integer(id));
        if (subject != null) {
            session.delete(subject);
        }
        System.out.println("Предмет удален. Детали предмета: " + subject);
    }

    @Override
    public Subject getSubjectById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Subject subject = (Subject) session.load(Subject.class, new Integer(id));
        System.out.println("Предметn загружен по id. Детали предмета: " + subject);
        return subject;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Subject> listSubjects() {
        System.out.println("****************ФАБРИКА ПРЕДМЕТОВ ОТКРЫТА*************************");
        Session session = this.sessionFactory.getCurrentSession();
        List<Subject> listSubjects = session.createQuery("FROM Subject ").list();
        for (Subject subject : listSubjects) {
            System.out.println("ПредметList: " + subject);
        }
        System.out.println("****************ФАБРИКА ЗАКРЫТА*************************");
        return listSubjects;
    }
}
