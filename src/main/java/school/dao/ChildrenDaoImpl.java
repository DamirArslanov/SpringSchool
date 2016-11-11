package school.dao;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import school.dao.interfaces.ChildrenDao;
import school.dao.interfaces.UserDao;
import school.entity.Children;

import java.util.List;

/**
 * Created by Cheshire on 23.09.2016.
 */
@Repository
public class ChildrenDaoImpl implements ChildrenDao {

    @Autowired
    public SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private UserDao userDao;
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }





    public void addChildren(Children children) {
        Session session = this.sessionFactory.getCurrentSession();
        children.setParent(this.userDao.tempUserForChildren(children));
        if (children.getSchoolClass() != null & children.getSchoolClass().getTeacher() != null) {
            children.setTeacher(children.getSchoolClass().getTeacher());
            children.setSchoolClass(children.getSchoolClass());
        }
        session.persist(children);
        System.out.println("Children добавлен. Детали ребёнка: " + children);
    }

    public void updateChildren(Children children) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(children);
        System.out.println("Children удален. Детали ребёнка: " + children);
    }

    public void removeChildren(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Children children = (Children) session.load(Children.class, new Integer(id));
        if (children != null) {
            session.delete(this.userDao.getUserByChildren(id));
            session.delete(children);
        }
        System.out.println("Children удален. Детали ребёнка: " + children);
    }

    @Override
    public Children getChildrenById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Children children = (Children) session.load(Children.class, new Integer(id));
        System.out.println("Children загружен по id. Детали ребёнка: " + children);
        return children;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Children> listChildrens() {
        System.out.println("****************ФАБРИКА УЧЕНИКОВ ОТКРЫТА*************************");
        Session session = this.sessionFactory.getCurrentSession();
        List<Children> listChildrens = session.createQuery("FROM Children").list();
        for (Children children : listChildrens) {
            System.out.println("ChildrenList: " + children);
        }
        System.out.println("****************ФАБРИКА ЗАКРЫТА*************************");
        return listChildrens;
    }
}
