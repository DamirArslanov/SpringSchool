package school.security.userSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Created by Cheshire on 18.10.2016.
 */
//@Service
//public class UserSecurityServiceImpl implements UserSecurityService {
//
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
//
//    private UserDetailsService userDetailsService;
//    @Autowired
//    public void setUserDetailsService(@Qualifier("parentsDetailsServiceImpl")UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//
//    }
//
//    @Override
//    public String findLoggedInUsername() {
//        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
//        if (userDetails instanceof UserDetails) {
//            return ((UserDetails) userDetails).getUsername();
//        }
//        return null;
//    }
//
//    @Override
//    public void autoLogin(String username, String password) {
//
//    }
//}
