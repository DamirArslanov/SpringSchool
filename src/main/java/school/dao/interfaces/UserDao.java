package school.dao.interfaces;

import school.entity.Children;
import school.entity.User;

import java.util.List;

/**
 * Created by Cheshire on 16.10.2016.
 */
public interface UserDao {

    public void addUser(User user);

    public User tempUserForChildren(Children children);

    public void updateUser(User user);

    public void removeUser(int id);

    public User getUserById(int id);

    public List<User> listUsers();

    public User getUserByChildren(int id);

    public User getUserByUsername(String username);

}
