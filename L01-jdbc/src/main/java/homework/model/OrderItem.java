package homework.model;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderItem {

    private final Long id;
    private final Long orderId;
    private final String name;
    private final int count;
    private final BigDecimal price;

    public OrderItem(Long id, Long orderId, String name, int count, BigDecimal price) {
        this.id = id;
        this.orderId = orderId;
        this.name = name;
        this.count = count;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem orderItem = (OrderItem) o;
        return getId().equals(orderItem.getId()) && getOrderId().equals(orderItem.getOrderId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOrderId());
    }
}
