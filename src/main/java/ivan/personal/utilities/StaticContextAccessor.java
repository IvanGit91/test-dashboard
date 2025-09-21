package ivan.personal.utilities;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
@Scope("singleton")
public class StaticContextAccessor {

    private static StaticContextAccessor instance;

    private final ApplicationContext applicationContext;

    public StaticContextAccessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        return instance.applicationContext.getBean(clazz);
    }

    @PostConstruct
    public void registerInstance() {
        instance = this;
    }
}
