package school.security.teacherSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Created by Cheshire on 09.10.2016.
 */
@Service
public class SchoolSecurityServiceImpl implements SchoolSecurityService {


    private AuthenticationManager authenticationManager;

    @Autowired
    public void setAuthenticationManager(@Qualifier("authenticationManager")AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    private UserDetailsService userDetailsService;

    @Autowired
    public void setUserDetailsService(@Qualifier("schoolDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Override
    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails) userDetails).getUsername();
        }
        return null;
    }

    @Override
    public void autoLogin(String username, String password) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            System.out.println("************АУТЕНТИФИКАЦИЯ ПРОШЛА УСПЕШНО!***************");
        }else System.out.println("************АУТЕНТИФИКАЦИЯ ПРОШЛА НЕУДАЧНО!***************");

    }
}
