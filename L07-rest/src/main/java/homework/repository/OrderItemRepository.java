package homework.repository;

import homework.dto.OrderItemDto;
import java.util.List;

public interface OrderItemRepository {

    Long addItem(OrderItemDto orderItemDto);

    void changeCount(Long orderId, Long orderItemId, int count);

    List<OrderItemDto> getOrderItemsList(Long orderId);

}
