package homework;

import ch.qos.logback.classic.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "homework.logback.enabled", havingValue = "true")
public class LoggerService {

    private final ApplicationContext appContext;

    @Autowired
    public LoggerService(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    public Logger getLogger(Class<?> clazz) {
        return appContext.getBean(Logger.class, clazz);
    }

}
