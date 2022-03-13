package homework;

import homework.model.Order;
import homework.model.OrderItem;
import homework.service.OrderService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DemoOrders {

    private final OrderService orderService;

    public DemoOrders(OrderService orderService) {
        this.orderService = orderService;
    }

    public void start() {
        var orderId = orderService.create(new Order("Ivanov"));
        orderService.addItem(new OrderItem(orderId, "Computer", 2, BigDecimal.valueOf(150000L)));
        orderService.addItem(new OrderItem(orderId, "Mouse", 2, BigDecimal.valueOf(1500L)));
        orderService.addItem(new OrderItem(orderId, "Display", 4, BigDecimal.valueOf(25000L)));

        var orderId2 = orderService.create(new Order("Petrov"));
        var orderItemId = orderService.addItem(new OrderItem(orderId2, "Router", 20, BigDecimal.valueOf(5000L)));

        orderService.displayOrder(orderId);
        orderService.displayOrder(orderId2);

        orderService.changeCount(orderId2, orderItemId, 19);
        orderService.displayOrder(orderId2);

        orderService.completeOrders();
        orderService.displayOrder(orderId);
        orderService.displayOrder(orderId2);
    }

}
