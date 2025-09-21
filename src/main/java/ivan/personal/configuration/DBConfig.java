package ivan.personal.configuration;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class DBConfig {

    private static final String PATH_SCAN_PACKAGE = "ivan.personal";

    @Resource
    private Environment env;

    @Bean
    public HibernateTemplate hibernateTemplate() {
        return new HibernateTemplate(sessionFactory());
    }

    @Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBuilder sessionFactoryBuilder = new LocalSessionFactoryBuilder(dataSource());
        sessionFactoryBuilder.scanPackages(PATH_SCAN_PACKAGE);
        sessionFactoryBuilder.addProperties(getHibernateProperties());
        return sessionFactoryBuilder.buildSessionFactory();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactory());
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.use_sql_comments", env.getRequiredProperty("hibernate.use_sql_comments"));
        properties.put("hibernate.enable_lazy_load_no_trans", env.getRequiredProperty("hibernate.enable_lazy_load_no_trans"));
        properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
        return properties;
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("datasource.driver.classname"));
        dataSource.setUrl(env.getRequiredProperty("datasource.connection.url"));
        dataSource.setUsername(env.getRequiredProperty("datasource.credential.user"));
        dataSource.setPassword(env.getRequiredProperty("datasource.credential.password"));
        dataSource.setDefaultAutoCommit(true);
        return dataSource;
    }
}

