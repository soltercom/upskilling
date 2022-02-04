package homework.repository;

import homework.model.OrderItem;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public interface OrderItemRepository {

    Long addItem(Connection connection, Long orderId, String name, int count, BigDecimal price);

    Long changeCount(Connection connection, Long orderId, Long orderItemId, int count);

    List<OrderItem> getOrderItemsList(Connection connection, Long orderId);

}
