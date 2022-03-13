package homework;

import homework.model.Order;
import homework.model.OrderItem;
import homework.model.OrderNotFoundException;
import homework.repository.OrderItemRepository;
import homework.repository.OrderRepository;
import homework.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Testcontainers
@Transactional
@SpringBootTest
class TestWithTestcontainer {

    @Container
    private final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:13")
            .withDatabaseName("test-orders")
            .withUsername("usr")
            .withPassword("pwd")
            .withClasspathResourceMapping("00_schema.sql", "/pg-docker-trans/00_schema.sql", BindMode.READ_WRITE);

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Test
    @DisplayName("#create should save Order to orders table and return created id")
    void createOrderTest() {
        var order = new Order("Test");

        var savedOrderId = orderService.create(order);

        assertNotNull(savedOrderId);

        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(
                (JdbcTemplate) template.getJdbcOperations(), "orders", "id = " + savedOrderId
        ));
    }

    @Test
    @DisplayName("#addItem should save OrderItem to order_items table and return created id")
    void addItemTest() {
        var order = new Order("Test");
        var orderId = orderService.create(order);

        var orderItem = new OrderItem(orderId, "Test Item", 2, BigDecimal.TEN);
        var orderItemId = orderService.addItem(orderItem);

        assertNotNull(orderItemId);

        assertEquals(1, JdbcTestUtils.countRowsInTableWhere(
                (JdbcTemplate) template.getJdbcOperations(), "order_items",
                String.format("id = %d AND order_id = %d", orderItemId, orderId)
        ));
    }

    @Test
    @DisplayName("#changeCount should change count in OrderItem")
    void changeCount() {
        var order = new Order("Test");
        var orderId = orderService.create(order);

        var orderItem = new OrderItem(orderId, "Test Item", 2, BigDecimal.TEN);
        var orderItemId = orderService.addItem(orderItem);

        orderService.changeCount(orderId, orderItemId, 1);
        var updatedOrder = orderRepository
            .findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));

        assertThat(updatedOrder.toString()).contains("count=1");
    }

    @Test
    void completeOrders() {
        var order = new Order("Test");
        orderService.create(order);
        order = new Order("Test 2");
        orderService.create(order);

        orderService.completeOrders();
        var listOfOrders = orderRepository.findAll();
        listOfOrders.forEach(o -> assertThat(o.toString()).contains("done=true"));
    }

}
