package homework.repository;

import homework.dto.OrderDto;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Long createOrder(Long clientId);

    Optional<OrderDto> getOrderById(Long orderId);

    List<OrderDto> getOrdersByClientId(Long clientId);

    void completeOrders();

}
