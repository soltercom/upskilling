package homework.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {

    private final Long id;
    private final Long clientId;
    private boolean done;
    private LocalDateTime updatedAt;

    public Order(Long id, Long clientId, boolean done, LocalDateTime updatedAt) {
        this.id = id;
        this.clientId = clientId;
        this.done = done;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getClientId() {
        return clientId;
    }

    public boolean isDone() {
        return done;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void complete() {
        this.done = true;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", clientId='" + clientId + '\'' +
                ", done=" + done +
                ", updatedAt=" + updatedAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getId().equals(order.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}

