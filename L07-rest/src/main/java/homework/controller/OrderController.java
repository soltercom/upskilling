package homework.controller;

import homework.dto.OrderDto;
import homework.dto.OrderItemDto;
import homework.exception.OrderNotFoundException;
import homework.repository.OrderItemRepository;
import homework.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Validated
@RestController
public class OrderController {

    private final OrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderRepository orderRepo, OrderItemRepository orderItemRepo) {
        this.orderRepo = orderRepo;
        this.orderItemRepo = orderItemRepo;
    }

    @PostMapping(path = "/order")
    ResponseEntity<URI> createOrder(@RequestParam Long clientId) {
        logger.info("Request: Create order (clientId={})", clientId);
        var id = orderRepo.createOrder(clientId);
        return ResponseEntity.created(URI.create("/order/" + id)).build();
    }

    @PostMapping(path = "/order/{orderId}/item")
    ResponseEntity<Void> addOrderItem(@PathVariable Long orderId, @RequestBody OrderItemDto orderItemDto) {
        logger.info("Request: Add order item (orderId={}, orderItemDto={})", orderId, orderItemDto);
        var id = orderItemRepo.addItem(orderItemDto);
        return ResponseEntity.created(URI.create("/order/" + orderId + "/item/" + id)).build();
    }

    @PatchMapping(path = "/order/{orderId}/item/{id}")
    ResponseEntity<Void> changeCount(@PathVariable Long orderId, @PathVariable Long id, @RequestParam int count) {
        logger.info("Request: Change item count (orderId={}, id={}, count={})", orderId, id, count);
        orderItemRepo.changeCount(orderId, id, count);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/order/{orderId}")
    ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId) {
        logger.info("Request: Get order by Id (orderId={})", orderId);
        return orderRepo.getOrderById(orderId)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @GetMapping(path = "/order")
    ResponseEntity<List<OrderDto>> getOrdersByClientId(@RequestParam Long clientId) {
        logger.info("Request: Get orders by client id (clientId={})", clientId);
        return ResponseEntity.ok(orderRepo.getOrdersByClientId(clientId));
    }

    @PatchMapping(path = "/order")
    ResponseEntity<Void> completeOrders() {
        logger.info("Request: Complete orders");
        orderRepo.completeOrders();
        return ResponseEntity.ok().build();
    }

}
