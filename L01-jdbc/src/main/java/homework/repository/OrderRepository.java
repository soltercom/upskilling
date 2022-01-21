package homework.repository;

import homework.model.Order;

import java.sql.Connection;
import java.util.Optional;

public interface OrderRepository {

    Long createOrder(Connection connection, String userName);

    Optional<Order> getOrderById(Connection connection, Long orderId);

    Boolean completeOrders(Connection connection);

}
