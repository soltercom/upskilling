package homework.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;

@Table("order_items")
public class OrderItem {

    @Id
    private final Long id;

    private final Long orderId;

    @NonNull
    private final String itemName;

    private final int itemCount;

    @NonNull
    private final BigDecimal itemPrice;

    public OrderItem(Long orderId, String itemName, int itemCount, BigDecimal itemPrice) {
        this(null, orderId, itemName, itemCount, itemPrice);
    }

    @PersistenceConstructor
    public OrderItem(Long id, Long orderId, @NonNull String itemName, int itemCount, @NonNull BigDecimal itemPrice) {
        this.id = id;
        this.orderId = orderId;
        this.itemName = itemName;
        this.itemCount = itemCount;
        this.itemPrice = itemPrice;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return String.format("{id=%d, orderId=%d, name=%s, count=%d, price=%s}",
            id, orderId, itemName, itemCount, itemPrice);
    }
}
