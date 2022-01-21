package homework.service;

import java.math.BigDecimal;

public interface OrderService {

    Long create(String user_name);

    Long addItem(Long orderId, String name, int count, BigDecimal price);

    void changeCount(Long orderId, Long orderItemId, int count);

    void displayOrder(Long orderId);

    void completeOrders();

}
