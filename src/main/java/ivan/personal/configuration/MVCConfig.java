package ivan.personal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@PropertySource("classpath:application.properties")
@EnableWebMvc
public class MVCConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/logout").setViewName("main");
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/home").setViewName("home/home");
        registry.addViewController("/sidebar").setViewName("sidebar/sidebar");
        registry.addViewController("/testresult").setViewName("testresult/testresult");
        registry.addViewController("/statistics").setViewName("statistics/statistics");
        registry.addViewController("/environments").setViewName("environments/environments");
        registry.addViewController("/databoard_home").setViewName("home/databoard/databoard_home");
        registry.addViewController("/chart").setViewName("chart/chart");
        registry.addViewController("/dash_chart").setViewName("dashchart/dash_chart");
        registry.addViewController("/tests_chart").setViewName("testschart/tests_chart");
        registry.addViewController("/availableTest").setViewName("availableTest/availableTest");
        registry.addViewController("/reportDetails").setViewName("reportDetails/reportDetails");
        registry.addViewController("/benchmark").setViewName("benchmark/benchmark");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }

    @Bean
    public InternalResourceViewResolver getViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/");
        registry.addResourceHandler("/fonts/**")
                .addResourceLocations("classpath:/fonts/");
        registry.addResourceHandler("/img/**")
                .addResourceLocations("classpath:/img/");
        registry.addResourceHandler("/views/**")
                .addResourceLocations("/WEB-INF/views/");
    }

    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
        messageBundle.setBasename("classpath:messages/messages");
        messageBundle.setDefaultEncoding("UTF-8");
        return messageBundle;
    }
}
