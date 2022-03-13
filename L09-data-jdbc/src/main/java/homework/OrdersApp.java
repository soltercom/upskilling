package homework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;

@SpringBootApplication
@EnableJdbcAuditing
public class OrdersApp {

    public static void main(String[] args) {
        var context = SpringApplication.run(OrdersApp.class, args);
        context.getBean(DemoOrders.class).start();
    }

}
