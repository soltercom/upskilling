package homework.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Order {

    private final Long id;
    private final String userName;
    private final boolean done;
    private final LocalDateTime updatedAt;

    public Order(Long id, String userName, boolean done, LocalDateTime updatedAt) {
        this.id = id;
        this.userName = userName;
        this.done = done;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isDone() {
        return done;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
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
