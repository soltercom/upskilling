package homework;

import homework.db.DbExecutorImpl;
import homework.db.DriverManagerDataSource;
import homework.db.TransactionRunnerJdbc;
import homework.repository.OrderItemRepositoryImpl;
import homework.repository.OrderRepositoryImpl;
import homework.service.OrderServiceImpl;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.math.BigDecimal;

public class OrdersApp {

    private static final Logger logger = LoggerFactory.getLogger(OrdersApp.class);

    private static final String URL = "jdbc:postgresql://0.0.0.0:5430/orders";
    private static final String USER = "usr";
    private static final String PASSWORD = "pwd";

    public static void main(String[] args) {

        var dataSource = new DriverManagerDataSource(URL, USER, PASSWORD);
        flywayMigrations(dataSource);

        var transactionRunner = new TransactionRunnerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();
        var orderRepo = new OrderRepositoryImpl(dbExecutor);
        var orderItemRepo = new OrderItemRepositoryImpl(dbExecutor);
        var orderService = new OrderServiceImpl(transactionRunner, orderRepo, orderItemRepo);

        var orderId = orderService.create("Computers");
        orderService.addItem(orderId, "Computer", 2, BigDecimal.valueOf(150000L));
        orderService.addItem(orderId, "Mouse", 2, BigDecimal.valueOf(1500L));
        orderService.addItem(orderId, "Display", 1, BigDecimal.valueOf(25000L));

        var orderId2 = orderService.create("Net equipment");
        var order2ItemId = orderService.addItem(orderId2, "Router", 20, BigDecimal.valueOf(5000L));

        orderService.displayOrder(orderId);
        orderService.displayOrder(orderId2);

        orderService.changeCount(orderId2, order2ItemId, 19);
        orderService.displayOrder(orderId2);

        orderService.completeOrders();
        orderService.displayOrder(orderId);
        orderService.displayOrder(orderId2);

    }

    private static void flywayMigrations(DataSource dataSource) {
        logger.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db")
                .load();
        flyway.migrate();
        logger.info("db migration finished.");
        logger.info("***");
    }


}
