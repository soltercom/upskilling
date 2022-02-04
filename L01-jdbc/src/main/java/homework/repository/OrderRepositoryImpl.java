package homework.repository;

import homework.db.DataTemplateException;
import homework.db.DbExecutor;
import homework.model.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OrderRepositoryImpl implements OrderRepository {

    private final DbExecutor dbExecutor;

    public OrderRepositoryImpl(DbExecutor dbExecutor) {
        this.dbExecutor = dbExecutor;
    }

    @Override
    public Long createOrder(Connection connection, String userName) {
        var sql = "INSERT INTO ORDERING(user_name, done, updated_at) VALUES(?, ?, ?)";
        return dbExecutor.executeStatement(connection, sql, List.of(userName, false, LocalDateTime.now()));
    }

    @Override
    public Optional<Order> getOrderById(Connection connection, Long orderId) {
        var sql = "SELECT * FROM ORDERING WHERE id = ?";
        return dbExecutor.executeSelect(connection, sql, List.of(orderId), rs -> {
            try {
                if (rs.next()) {
                    return new Order(rs.getLong("id"),
                                     rs.getString("user_name"),
                                     rs.getBoolean("done"),
                                     rs.getTimestamp("updated_at").toLocalDateTime());
                } else {
                    return null;
                }
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public Boolean completeOrders(Connection connection) {
        var sql = "UPDATE ORDERING SET done = TRUE, updated_at = ? WHERE done = FALSE";
        dbExecutor.executeStatement(connection, sql, List.of(LocalDateTime.now()));
        return true;
    }
}
