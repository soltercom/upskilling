package homework.service;

import homework.model.Order;
import homework.model.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {

    Long create(String userName);

    Long addItem(Long orderId, String name, int count, BigDecimal price);

    void changeCount(Long orderId, Long orderItemId, int count);

    void displayOrder(Long orderId);

    void completeOrders();

    // for test purpose only
    Order getOrderById(Long orderId);
    List<OrderItem> getOrderList(Long orderId);

}
