package school.security.userSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.entity.User;
import school.service.interfaces.UserService;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Cheshire on 18.10.2016.
 */
//@Service("parentsDetailsServiceImpl")
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    UserService userService;
//
//    @Autowired
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }
//
//
//    @Override
//    @Transactional(readOnly = true)
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User parent = this.userService.getUserByUsername(username);
//
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//
//
//        org.springframework.security.core.userdetails.User securityUser =
//                new org.springframework.security.core.userdetails.User(parent.getUsername(), parent.getPassword(), grantedAuthorities);
//        return securityUser;
//    }
//}
