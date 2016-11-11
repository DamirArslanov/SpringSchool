package school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.dao.interfaces.UserDao;
import school.entity.Children;
import school.entity.User;
import school.service.interfaces.UserService;

import java.util.List;

/**
 * Created by Cheshire on 16.10.2016.
 */
@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        this.userDao.addUser(user);
    }

    @Override
    @Transactional
    public User tempUserForChildren(Children children) {
        return this.userDao.tempUserForChildren(children);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        this.userDao.updateUser(user);
    }

    @Override
    @Transactional
    public void removeUser(int id) {
        this.userDao.removeUser(id);
    }

    @Override
    @Transactional
    public User getUserById(int id) {
        return this.userDao.getUserById(id);
    }

    @Override
    @Transactional
    public List<User> listUsers() {
        return this.userDao.listUsers();
    }


    @Override
    @Transactional
    public User getUserByChildren(int id) {
        return this.userDao.getUserByChildren(id);
    }

    @Override
    @Transactional
    public User getUserByUsername(String username) {
        return this.userDao.getUserByUsername(username);
    }
}
