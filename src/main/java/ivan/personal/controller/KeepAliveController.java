package ivan.personal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

// UNUSED

@RestController
@RequestMapping("/keepalive")
public class KeepAliveController {

    public static final Logger logger = LoggerFactory.getLogger(KeepAliveController.class);

    @GetMapping("/")
    public ResponseEntity<Principal> isLogged(Principal principal, HttpServletRequest request, HttpServletResponse response) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("DASHBOARDSESSIONID")) {
                logger.info(cookie.getName());
                logger.info(cookie.getValue());
                cookie.setMaxAge(30);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }

        return new ResponseEntity<>(principal, HttpStatus.OK);
    }

}
