package homework.repository;

import homework.db.DataTemplateException;
import homework.db.DbExecutor;
import homework.model.OrderItem;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderItemRepositoryImpl implements OrderItemRepository {

    private final DbExecutor dbExecutor;

    public OrderItemRepositoryImpl(DbExecutor dbExecutor) {
        this.dbExecutor = dbExecutor;
    }

    @Override
    public Long addItem(Connection connection, Long orderId, String name, int count, BigDecimal price) {
        var sql = "INSERT INTO ORDERING_ITEMS(ordering_id, item_name, item_count, item_price) VALUES(?, ?, ?, ?)";
        var orderItemId = dbExecutor.executeStatement(connection, sql, List.of(orderId, name, count, price));

        sql = "UPDATE ORDERING SET updated_at = ? WHERE id = ?";
        dbExecutor.executeStatement(connection, sql, List.of(LocalDateTime.now(), orderId));

        return orderItemId;
    }

    @Override
    public Long changeCount(Connection connection, Long orderId, Long orderItemId, int count) {
        var sql = "UPDATE ORDERING_ITEMS SET item_count=? WHERE ordering_id=? AND id=?";
        dbExecutor.executeStatement(connection, sql, List.of(count, orderId, orderItemId));

        sql = "UPDATE ORDERING SET updated_at = ? WHERE id = ?";
        dbExecutor.executeStatement(connection, sql, List.of(LocalDateTime.now(), orderId));

        return 1L;
    }

    @Override
    public List<OrderItem> getOrderItemsList(Connection connection, Long orderId) {
        var sql = "SELECT * FROM ORDERING_ITEMS WHERE ordering_id = ?";
        return dbExecutor.executeSelect(connection, sql, List.of(orderId), rs -> {
            var orderItemList = new ArrayList<OrderItem>();
            try {
                while (rs.next()) {
                    orderItemList.add(new OrderItem(rs.getLong("id"),
                                                    rs.getLong("ordering_id"),
                                                    rs.getString("item_name"),
                                                    rs.getInt("item_count"),
                                                    rs.getBigDecimal("item_price")));
                }
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
            return orderItemList;
        }).orElseThrow(() -> new RuntimeException("Problems with fetching ORDERING_ITEMS"));
    }
}
