package homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import homework.dto.OrderItemDto;
import homework.utils.IdGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("POST /order should return Created 201 /order/{id}")
    void createOrderTest() throws Exception {
        var orderId = getOrderId() + 1;
        this.mvc.perform(post("/order?clientId=1"))
            .andExpect(status().isCreated())
            .andExpect(redirectedUrl("/order/" + orderId));
    }

    @Test
    @DisplayName("POST /order/{orderId}/item should return Created 201 /order/{orderId}/item/{id}")
    void addOrderItemTest() throws Exception {
        var mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(new OrderItemDto(1L, "New Item", 10, BigDecimal.ONE));
        var orderId = getOrderId() + 1;
        var orderItemId = getOrderItemId() + 1;

        this.mvc.perform(post("/order?clientId=1"));

        this.mvc.perform(post("/order/" + orderId + "/item")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrl("/order/" + orderId + "/item/" + orderItemId));
    }

    @Test
    @DisplayName("GET /order/-1 should return 404 Not found")
    void orderNotFoundTest() throws Exception {
        this.mvc.perform(get("/order/-1"))
            .andExpect(status().isNotFound())
            .andExpect(content().string("Order with id -1 not found."));
    }

    @Test
    @DisplayName("PATCH /order/{orderId}/item/{id}?count=555 should change an order item count")
    void changeCountTest() throws Exception {
        createOrderWithTwoItems(1L);
        var orderId = getOrderId();
        var orderItemId = getOrderItemId();
        this.mvc.perform(patch("/order/" + orderId + "/item/" + orderItemId + "?count=555"));

        this.mvc.perform(get("/order/" + orderId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['items'][1].count", is(555)));
    }

    @Test
    @DisplayName("GET /order/{orderID} should return json representation of this Order")
    void getOrderTest() throws Exception {
        createOrderWithTwoItems(1L);
        var orderId = getOrderId();

        this.mvc.perform(get("/order/" + orderId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$['clientId']", is(1)))
            .andExpect(jsonPath("$['items'].length()", is(2)))
            .andExpect(jsonPath("$['items'][1].name",is("New Item 2")));
    }

    @Test
    @DisplayName("GET /order?clientId=1 should return all orders of this client")
    void getOrdersByClientIdTest() throws Exception {
        createOrderWithTwoItems(2L);

        this.mvc.perform(get("/order?clientId=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]['clientId']", is(2)))
                .andExpect(jsonPath("$[0]['items'].length()", is(2)))
                .andExpect(jsonPath("$[0]['items'][1].name", is("New Item 2")));
    }

    @Test
    @DisplayName("PATCH /order should complete all orders")
    void completeOrdersTest() throws Exception {
        createOrderWithTwoItems(1L);

        this.mvc.perform(patch("/order"))
            .andExpect(status().isOk());

        this.mvc.perform(get("/order?clientId=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]['done']", is(true)));
    }

    private void createOrderWithTwoItems(long clientId) throws Exception {
        var mapper = new ObjectMapper();
        var orderId = getOrderId() + 1;
        var json1 = mapper.writeValueAsString(new OrderItemDto(orderId, "New Item 1", 10, BigDecimal.ONE));
        var json2 = mapper.writeValueAsString(new OrderItemDto(orderId, "New Item 2", 10, BigDecimal.ONE));

        this.mvc.perform(post("/order?clientId=" + clientId));
        this.mvc.perform(post("/order/" + orderId + "/item")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json1));
        this.mvc.perform(post("/order/" + orderId + "/item")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json2));
    }

    private long getOrderId() {
        return IdGenerator.getCurrentOrderId();
    }

    private long getOrderItemId() {
        return IdGenerator.getCurrentOrderItemId();
    }
}
