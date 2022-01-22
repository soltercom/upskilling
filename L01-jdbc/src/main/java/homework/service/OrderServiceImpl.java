package homework.service;

import homework.db.TransactionRunner;
import homework.model.OrderNotFound;
import homework.repository.OrderItemRepository;
import homework.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final TransactionRunner transactionRunner;
    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;

    public OrderServiceImpl(TransactionRunner transactionRunner, OrderRepository orderRepo, OrderItemRepository orderItemRepo) {
        this.transactionRunner = transactionRunner;
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
    }

    @Override
    public Long create(String userName) {
        var orderId =  transactionRunner.doInTransaction(connection ->
            orderRepo.createOrder(connection, userName));
        logger.info("Created order: {}, {}", orderId, userName);
        return orderId;
    }

    @Override
    public Long addItem(Long orderId, String name, int count, BigDecimal price) {
        var orderItemId = transactionRunner.doInTransaction(connection ->
            orderItemRepo.addItem(connection, orderId, name, count, price));
        logger.info("Order Item created: {}, {}, {}", name, count, price);
        return orderItemId;
    }

    @Override
    public void changeCount(Long orderId, Long orderItemId, int count) {
        transactionRunner.doInTransaction(connection ->
            orderItemRepo.changeCount(connection, orderId, orderItemId, count));
        logger.info("Order Item {} {} changed count to {}", orderId, orderItemId, count);
    }

    @Override
    public void displayOrder(Long orderId) {
        var order = transactionRunner.doInTransaction(connection ->
            orderRepo.getOrderById(connection, orderId))
                .orElseThrow(() -> new OrderNotFound(orderId));

        var orderItemList = transactionRunner.doInTransaction(connection ->
            orderItemRepo.getOrderItemsList(connection, orderId));

        logger.info("======ORDER======");
        var orderInfo = order.toString();
        logger.info(orderInfo);
        for (var orderItem: orderItemList) {
            var orderItemInfo = orderItem.toString();
            logger.info(orderItemInfo);
        }
        logger.info("=================");

    }

    @Override
    public void completeOrders() {
        transactionRunner.doInTransaction(orderRepo::completeOrders);
        logger.info("Orders completed");
    }
}
