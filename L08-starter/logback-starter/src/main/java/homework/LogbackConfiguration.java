package homework;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.FileAppender;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ConditionalOnClass(Logger.class)
@EnableConfigurationProperties(LogbackProperties.class)
@ConditionalOnProperty(value = "homework.logback.enabled", havingValue = "true")
public class LogbackConfiguration {

    private final LogbackProperties logbackProperties;

    public LogbackConfiguration(LogbackProperties logbackProperties) {
        this.logbackProperties = logbackProperties;
    }

    @Bean
    public Class<?> defaultClass() {
        return LogbackConfiguration.class;
    }

    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    Logger getLogger(Class<?> clazz) {
        var logCtx = (LoggerContext) LoggerFactory.getILoggerFactory();
        logCtx.reset();

        var appender = new FileAppender<ILoggingEvent>();
        appender.setContext(logCtx);
        appender.setFile(logbackProperties.getFile());
        appender.setImmediateFlush(true);

        var encoder = new PatternLayoutEncoder();
        encoder.setContext(logCtx);
        encoder.setPattern("%d %-5level %logger{35} - %msg %n");
        encoder.start();

        appender.setEncoder(encoder);
        appender.start();

        Logger logger = (Logger) LoggerFactory.getLogger(clazz);
        logger.addAppender(appender);

        return logger;
    }

}
