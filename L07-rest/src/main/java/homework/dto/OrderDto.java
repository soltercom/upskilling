package homework.dto;

import homework.model.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDto {

    private Long id;
    private Long clientId;
    private boolean done;
    private LocalDateTime updatedAt;
    private List<OrderItemDto> items;

    public OrderDto(Order order, List<OrderItemDto> items) {
        this.id = order.getId();
        this.clientId = order.getClientId();
        this.done = order.isDone();
        this.updatedAt = order.getUpdatedAt();
        this.items = new ArrayList<>(items);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", done=" + done +
                ", updatedAt=" + updatedAt +
                ", items=" + items +
                '}';
    }

}
