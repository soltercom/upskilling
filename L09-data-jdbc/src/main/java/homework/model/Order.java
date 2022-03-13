package homework.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table("orders")
public class Order {

    @Id
    private final Long id;

    @NonNull
    private final String userName;

    private final boolean done;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @MappedCollection(idColumn = "order_id", keyColumn = "id")
    private final List<OrderItem> orderItems;

    public Order(String userName) {
        this(null, userName, false, new ArrayList<>());
    }

    @PersistenceConstructor
    public Order(Long id, @NonNull String userName, boolean done, List<OrderItem> orderItems) {
        this.id = id;
        this.userName = userName;
        this.done = done;
        this.orderItems = orderItems;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();

        sb.append("{")
            .append("id").append("=").append(id).append(", ")
            .append("userName").append("=").append(userName).append(", ")
            .append("done").append("=").append(done).append(", ")
            .append("updatedAt").append("=").append(updatedAt).append(", ")
            .append("orderItems: ").append("[");

        var iterator = orderItems.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }

        sb.append("]}");

        return sb.toString();
    }

}
