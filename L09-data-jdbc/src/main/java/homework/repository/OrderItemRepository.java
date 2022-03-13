package homework.repository;

import homework.model.OrderItem;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {

    @Modifying
    @Query("""
        UPDATE order_items SET item_count=:count WHERE order_id=:order_id AND id=:id
    """)
    void changeCount(@Param("order_id") Long orderId, @Param("id") Long id, @Param("count") int count);

}
