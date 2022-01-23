import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import homework.db.DbExecutorImpl;
import homework.db.TransactionRunnerJdbc;
import homework.repository.OrderItemRepository;
import homework.repository.OrderItemRepositoryImpl;
import homework.repository.OrderRepository;
import homework.repository.OrderRepositoryImpl;
import homework.service.OrderService;
import homework.service.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class TestWithTestcontainers {

    private static final Logger logger = LoggerFactory.getLogger(TestWithTestcontainers.class);

    @Container
    private final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test-orders")
            .withUsername("usr")
            .withPassword("pwd")
            .withClasspathResourceMapping("00_init.sql", "/docker-entrypoint-initdb.d/00_init.sql", BindMode.READ_WRITE);

    private OrderService getOrderService() {
        var config = new HikariConfig();
        config.setJdbcUrl(postgresqlContainer.getJdbcUrl());
        config.setAutoCommit(false);
        config.setUsername("usr");
        config.setPassword("pwd");
        var dataSource = new HikariDataSource(config);

        var transactionRunner = new TransactionRunnerJdbc(dataSource);
        var dbExecutor = new DbExecutorImpl();
        var orderRepo = new OrderRepositoryImpl(dbExecutor);
        var orderItemRepo = new OrderItemRepositoryImpl(dbExecutor);
        return new OrderServiceImpl(transactionRunner, orderRepo, orderItemRepo);
    }

    @Test
    void createTest() {
        var orderService = getOrderService();

        var expectedName = "Test Order";
        var orderId = orderService.create(expectedName);

        var order = orderService.getOrderById(orderId);
        assertEquals(expectedName, order.getUserName());
        assertEquals(orderId, order.getId());

        var expectedItemName = "Item Order";
        var expectedCount = 10;
        var expectedPrice = new BigDecimal("10.00");
        orderService.addItem(orderId, expectedItemName, expectedCount, expectedPrice);

        var orderItemList = orderService.getOrderList(orderId);
        assertEquals(1, orderItemList.size());
        assertEquals(expectedItemName, orderItemList.get(0).getName());
        assertEquals(expectedCount   , orderItemList.get(0).getCount());
        assertEquals(expectedPrice   , orderItemList.get(0).getPrice());
    }

}
