package ivan.personal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;


@SpringBootApplication
public class DashboardMain extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(DashboardMain.class, args);
    }
}
