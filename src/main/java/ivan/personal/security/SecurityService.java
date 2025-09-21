package ivan.personal.security;

import ivan.personal.entity.User;
import ivan.personal.service.UserService;
import ivan.personal.utilities.StaticContextAccessor;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Aspect
@Scope("prototype")
@RequiredArgsConstructor
public class SecurityService {

    private final UserService userService;
    private final Environment environment;

    // add authority run time to current user
    public void addAuthorities(String authorities) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(auth.getAuthorities());
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(authorities);
        updatedAuthorities.add(authority);

        Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(), updatedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    public String getPasswordPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return user.getPassword();
    }

    private boolean securityEnable() {
        return environment.getRequiredProperty("security.web.enable").equals("true");
    }

    public String getUserPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        return user.getUsername();
    }

    public User getPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (User) auth.getPrincipal();
    }

    public int getUserConnected() {
        SessionRegistry sessionRegistry = StaticContextAccessor.getBean(SessionRegistry.class);
        return sessionRegistry.getAllPrincipals().size();
    }

    public boolean hasRole(String role) {

        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null)
            return false;

        Authentication authentication = context.getAuthentication();
        if (authentication == null)
            return false;

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (role.equals(auth.getAuthority()))
                return true;
        }

        return false;
    }
}
