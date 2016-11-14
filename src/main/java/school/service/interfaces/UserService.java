package school.service.interfaces;

import school.entity.Children;
import school.entity.User;

import java.util.List;

/**
 * Created by ArslanovDamir on 16.10.2016.
 */
public interface UserService {

    public void addUser(User user);

    public User tempUserForChildren(Children children);

    public void updateUser(User user);

    public void removeUser(int id);

    public User getUserById(int id);

    public List<User> listUsers();

    public User getUserByChildren(int id);

    public User getUserByUsername(String username);
}
