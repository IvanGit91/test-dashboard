package ivan.personal.security;

import ivan.personal.entity.User;
import ivan.personal.service.UserService;
import ivan.personal.utilities.StaticContextAccessor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("customUserDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if (null == user) {
            throw new UsernameNotFoundException("No user present with username: " + username);
        } else {
            UserDetailsImpl userDetails = StaticContextAccessor.getBean(UserDetailsImpl.class);
            userDetails.setUser(user);
            return userDetails;
        }
    }
}
