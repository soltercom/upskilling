package homework;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogbackStarterExample implements ApplicationRunner {


    public static void main(String[] args) {
        SpringApplication.run(LogbackStarterExample.class, args);
    }

    private final LoggerService service;

    public LogbackStarterExample(@Autowired(required = false) LoggerService service) {
        this.service = service;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (service != null) {
            var logger = service.getLogger(LogbackStarterExample.class);
            logger.info("TEST");
        }
    }
}
