package homework.service;

import homework.model.Order;
import homework.model.OrderItem;

public interface OrderService {

    Long create(Order order);

    Long addItem(OrderItem orderItem);

    void changeCount(Long orderId, Long orderItemId, int count);

    void displayOrder(Long id);

    void completeOrders();

}
