package homework.repository;

import homework.dto.OrderDto;
import homework.model.Order;
import homework.utils.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderRepositoryImpl implements OrderRepository {

    private static final Logger logger = LoggerFactory.getLogger(OrderRepositoryImpl.class);

    private final Map<Long, Order> orders = new HashMap<>();
    private final OrderItemRepository orderItemRepo;

    public OrderRepositoryImpl(OrderItemRepository orderItemRepo) {
        this.orderItemRepo = orderItemRepo;
    }

    @Override
    public Long createOrder(Long clientId) {
        var id = IdGenerator.getNextOrderId();
        var order = new Order(id, clientId, false, LocalDateTime.now());
        orders.put(id, order);
        logger.info("Create order: {}", order);
        return id;
    }

    @Override
    public Optional<OrderDto> getOrderById(Long orderId) {
        var order = orders.get(orderId);
        if (order == null) {
            logger.info("Order with id {} not found", orderId);
            return Optional.empty();
        } else {
            var items = orderItemRepo.getOrderItemsList(orderId);
            var orderDto = new OrderDto(order, items);
            logger.info("Order DTO returned: {}", orderDto);
            return Optional.of(orderDto);
        }
    }

    @Override
    public List<OrderDto> getOrdersByClientId(Long clientId) {
        var orderList =  orders.values().stream()
            .filter(o -> o.getClientId().equals(clientId))
            .map(o -> getOrderById(o.getId()))
            .map(o -> o.orElse(null))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        logger.info("Order list returned: {}", orderList);
        return orderList;
    }

    @Override
    public void completeOrders() {
        for (var order: orders.values()) {
            order.complete();
            logger.info("Order completed: {}", order);
        }
    }
}
