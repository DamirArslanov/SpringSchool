package school.security.teacherSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.dao.interfaces.TeacherDao;
import school.dao.interfaces.UserDao;
import school.entity.Teacher;
import school.security.TRole;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Cheshire on 09.10.2016.
 */
@Service("schoolDetailsServiceImpl")
public class SchoolDetailsServiceImpl implements UserDetailsService {

    TeacherDao teacherDao;

    @Autowired
    private void setTeacherDao(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        if(name.substring(0,4).equals("user")) {
            school.entity.User parent = this.userDao.getUserByUsername(name);
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            User securityUser = new User(parent.getUsername(), parent.getPassword(), grantedAuthorities);
            return securityUser;
        } else {
            Teacher teacher = this.teacherDao.getTeacherByUsername(name);
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            for (TRole role : teacher.getRoles()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
            }
            User user = new User(teacher.getUsername(), teacher.getPassword(), grantedAuthorities);
            return user;
        }


    }
}
