package homework.repository;

import homework.dto.OrderItemDto;
import homework.model.OrderItem;
import homework.utils.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderItemRepositoryImpl implements OrderItemRepository {

    private static final Logger logger = LoggerFactory.getLogger(OrderItemRepositoryImpl.class);

    private final Map<Long, List<OrderItem>> orderItems = new HashMap<>();

    @Override
    public Long addItem(OrderItemDto orderItemDto) {
        var id = IdGenerator.getNextOrderItemId();
        var orderId = orderItemDto.getOrderId();
        var item = new OrderItem(id, orderId,
                orderItemDto.getName(),
                orderItemDto.getCount(),
                orderItemDto.getPrice());

        var list = orderItems.getOrDefault(orderId, new ArrayList<>());
        list.add(item);
        orderItems.put(orderId, list);

        logger.info("Order item has been added {}", item);

        return id;
    }

    @Override
    public void changeCount(Long orderId, Long orderItemId, int count) {
        orderItems.get(orderId).stream()
            .filter(i -> i.getId().equals(orderItemId))
            .findAny()
            .ifPresent(orderItem -> {
                orderItem.changeCount(count);
                logger.info("Order item count has been changed {}", orderItem);
            });
    }

    @Override
    public List<OrderItemDto> getOrderItemsList(Long orderId) {
        return orderItems.getOrDefault(orderId, new ArrayList<>())
            .stream().map(OrderItemDto::new)
            .collect(Collectors.toList());
    }
}
