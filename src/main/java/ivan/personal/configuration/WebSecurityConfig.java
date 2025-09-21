package ivan.personal.configuration;

import ivan.personal.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ComponentScan(basePackageClasses = CustomUserDetailsService.class)
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService customUserDetailsService;
    private final Environment env;
    private final DataSource dataSource;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/lib/**");
        web.ignoring().antMatchers("/img/**");
        web.ignoring().antMatchers("/jsonssss/**");
        web.ignoring().antMatchers("/views/*.css");
        web.ignoring().antMatchers("/views/*.js");
        web.ignoring().antMatchers("/views/*/*.css");
        web.ignoring().antMatchers("/views/*/*.js");
        web.ignoring().antMatchers("/views/*/*/*.css");
        web.ignoring().antMatchers("/views/*/*/*.js");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.csrf().disable();
        http.sessionManagement().maximumSessions(Integer.parseInt(env.getRequiredProperty("maxUserConnected"))).expiredUrl("/login").sessionRegistry(sessionRegistry()); // to catch logged users
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        if (env.getRequiredProperty("security.web.enable").equals("true")) {
            http.authorizeRequests() // remember to respect the order of permissions
                    .antMatchers("/login", "/", "/home").permitAll()
                    .anyRequest().authenticated()
                    .and().formLogin().loginPage("/login").defaultSuccessUrl("/main")
                    .usernameParameter("user").passwordParameter("pass")
                    .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/main")
                    .and().sessionManagement().invalidSessionUrl("/login");
        }
    }


    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() { // used to register logged users
        return new HttpSessionEventPublisher();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        tokenRepositoryImpl.setDataSource(dataSource);
        return tokenRepositoryImpl;
    }
}