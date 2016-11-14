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


    public SessionFactory sessionFactory;
    @Autowired
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
    }

    public void updateChildren(Children children) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(children);
    }

    public void removeChildren(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Children children = (Children) session.load(Children.class, new Integer(id));
        if (children != null) {
            if (children.getParent() != null) {
                session.delete(this.userDao.getUserByChildren(id));
            }
            session.delete(children);
        }
    }

    @Override
    public Children getChildrenById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Children children = (Children) session.load(Children.class, new Integer(id));
        return children;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Children> listChildrens() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Children> listChildrens = session.createQuery("FROM Children").list();
        return listChildrens;
    }
}
