package ivan.personal.configuration;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;

@Configuration
public class ServletConfig {

    @Resource
    private Environment env;

    // Set maxPostSize of embedded tomcat server to 10 megabytes (default is 2 MB, not large enough to support file uploads > 1.5 MB)
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return (ConfigurableEmbeddedServletContainer container) -> {
            container.setPort(Integer.parseInt(env.getProperty("server.port")));
            container.setSessionTimeout(Integer.parseInt(env.getProperty("server.session.timeout")));

            if (container instanceof TomcatEmbeddedServletContainerFactory) {
                TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;
                tomcat.addConnectorCustomizers(
                        connector -> connector.setMaxPostSize(2000000000) // 2GB
                );
            }
        };
    }
}
