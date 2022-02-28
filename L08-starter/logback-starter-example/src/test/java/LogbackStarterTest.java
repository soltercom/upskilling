import ch.qos.logback.classic.Logger;
import homework.LogbackStarterExample;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = LogbackStarterExample.class)
@ConditionalOnProperty(value = "homework.logback.enabled", havingValue = "true")
class LogbackStarterTest {

    @Autowired(required = false)
    private Logger logger;

    @Test
    void loggerEnabled() {
        assertNotNull(logger);
    }

}
