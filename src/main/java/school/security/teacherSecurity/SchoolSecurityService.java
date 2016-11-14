package school.security.teacherSecurity;

/**
 * Created by ArslanovDamir on 09.10.2016.
 */
public interface SchoolSecurityService {

    String findLoggedInUsername();

    void autoLogin(String username, String password);
}
