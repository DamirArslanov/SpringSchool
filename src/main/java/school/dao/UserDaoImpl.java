package school.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import school.dao.interfaces.UserDao;
import school.entity.Children;
import school.entity.Teacher;
import school.entity.User;
import school.utils.Generator;

import java.util.List;

/**
 * Created by Cheshire on 16.10.2016.
 */
@Component
public class UserDaoImpl implements UserDao {

    private Generator generator;
    @Autowired
    public void setGenerator(Generator generator) {
        this.generator = generator;
    }

    public SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(user);
    }

    @Override
    public User tempUserForChildren(Children children) {
        Session session = this.sessionFactory.getCurrentSession();
        User user = new User();
        user.setChildren(children);
        user.setParentName("Родитель " + children.getFIO());
        user.setPassword(generator.simplePassGenerator());

        Boolean trying = false;
        while (trying == false) {
            String username = generator.simpleUsernameGenerator("user");
            String hql = "FROM User user WHERE user.username = (:username)";
            Query query = session.createQuery(hql).setParameter("username", username);
            if (query.list().isEmpty()) {
                user.setUsername(username);
                trying = true;
            }
        }
        session.persist(user);
        return user;
    }

    @Override
    public void updateUser(User user) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(user);
    }

    @Override
    public void removeUser(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        User user = (User) session.load(User.class, new Integer(id));
        if (user!=null) {
            session.delete(user);
        }
    }

    @Override
    public User getUserById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        User user = (User) session.load(User.class, new Integer(id));
        return user;
    }

    @Override
    public List<User> listUsers() {
        Session session = this.sessionFactory.getCurrentSession();
        List<User> listUsers = session.createQuery("FROM User").list();
        return listUsers;
    }

    @Override
    public User getUserByChildren(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        String hql = "SELECT children.parent FROM Children children WHERE children.ch_id = (:id)";
        Query query = session.createQuery(hql).setParameter("id", id);
        User user = (User) query.getSingleResult();
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        Session session = this.sessionFactory.getCurrentSession();
        String hql = "FROM User user WHERE user.username = (:username)";
        Query query = session.createQuery(hql).setParameter("username", username);
        User user = (User) query.getSingleResult();
        return user;
    }
}
