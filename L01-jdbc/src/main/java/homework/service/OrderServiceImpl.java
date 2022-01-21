package homework.service;

import homework.OrdersApp;
import homework.db.TransactionRunner;
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
        logger.info("Created order: {0}, {1}", orderId, userName);
        return orderId;
    }

    @Override
    public Long addItem(Long orderId, String name, int count, BigDecimal price) {
        var orderItemId = transactionRunner.doInTransaction(connection ->
            orderItemRepo.addItem(connection, orderId, name, count, price));
        logger.info("Order Item created: {0}, {1}, {2}", name, count, price);
        return orderItemId;
    }

    @Override
    public void changeCount(Long orderId, Long orderItemId, int count) {
        transactionRunner.doInTransaction(connection ->
            orderItemRepo.changeCount(connection, orderId, orderItemId, count));
        logger.info("Order Item {0} {1} changed count to {2}", orderId, orderItemId, count);
    }

    @Override
    public void displayOrder(Long orderId) {
        var order = transactionRunner.doInTransaction(connection ->
            orderRepo.getOrderById(connection, orderId));

        var orderItemList = transactionRunner.doInTransaction(connection ->
            orderItemRepo.getOrderItemsList(connection, orderId));

        logger.info("======ORDER======");
        logger.info(order.toString());
        for (var orderItem: orderItemList) {
            logger.info(orderItem.toString());
        }
        logger.info("=================");

    }

    @Override
    public void completeOrders() {
        transactionRunner.doInTransaction(orderRepo::completeOrders);
        logger.info("Orders completed");
    }
}
