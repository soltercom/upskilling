package homework.dto;

import homework.model.OrderItem;

import java.math.BigDecimal;

public class OrderItemDto {

    private Long id;
    private Long orderId;
    private String name;
    private int count;
    private BigDecimal price;

    public OrderItemDto(Long orderId, String name, int count, BigDecimal price) {
        this.id = null;
        this.orderId = orderId;
        this.name = name;
        this.count = count;
        this.price = price;
    }

    public OrderItemDto(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.orderId = orderItem.getOrderId();
        this.name = orderItem.getName();
        this.count = orderItem.getCount();
        this.price = orderItem.getPrice();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", price=" + price +
                '}';
    }
}
