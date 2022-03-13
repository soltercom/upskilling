package homework.repository;

import homework.model.Order;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {

    @Modifying
    @Query("update orders set done = true where done = false")
    void completeOrders();

}
