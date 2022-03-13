package homework.service;

import homework.model.Order;
import homework.model.OrderItem;
import homework.model.OrderNotFoundException;
import homework.repository.OrderItemRepository;
import homework.repository.OrderRepository;
import homework.utils.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final TransactionManager transactionManager;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemsRepo;

    public OrderServiceImpl(TransactionManager transactionManager,
                            OrderRepository orderRepo,
                            OrderItemRepository orderItemsRepo) {
        this.transactionManager = transactionManager;
        this.orderRepo = orderRepo;
        this.orderItemsRepo = orderItemsRepo;
    }

    @Override
    public Long create(Order order) {
        var createdOrder = transactionManager.doInTransaction(() -> {
            var savedOrder = orderRepo.save(order);
            logger.info("Order is saved: {}", savedOrder);
            return savedOrder;
        });
        return createdOrder.getId();
    }

    @Override
    public Long addItem(OrderItem orderItem) {
        var createdOrderItem = transactionManager.doInTransaction(() -> {
            var savedOrderItem = orderItemsRepo.save(orderItem);
            logger.info("OrderItem is saved: {}", savedOrderItem);
            return savedOrderItem;
        });
        return createdOrderItem.getId();
    }

    @Override
    @Modifying
    public void changeCount(Long orderId, Long orderItemId, int count) {
        transactionManager.doInTransaction(() -> {
            orderItemsRepo.changeCount(orderId, orderItemId, count);
            logger.info("OrderItem orderId={}, id={}  count is changed to: {}", orderId, orderItemId, count);
            return true;
        });
    }

    @Override
    public void displayOrder(Long id) {
        var order = orderRepo.findById(id)
            .orElseThrow(() -> new OrderNotFoundException(id));
        logger.info("======ORDER======");
        logger.info("{}", order);
        logger.info("=================");
    }

    @Override
    public void completeOrders() {
        transactionManager.doInTransaction(() -> {
            orderRepo.completeOrders();
            logger.info("Orders are completed.");
            return true;
        });
    }
}
