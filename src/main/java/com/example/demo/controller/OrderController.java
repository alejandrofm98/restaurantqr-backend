package com.example.demo.controller;

import static com.example.demo.utils.Constants.*;

import com.example.demo.config.Log4j2Config;
import com.example.demo.dto.OrderRequest;
import com.example.demo.entity.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.services.OrderService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CONSTANT_SECURE_URL)
@PreAuthorize("hasRole('" + CONSTANT_ROL_OWNER + "') or hasRole('" + CONSTANT_ROL_ADMIN + "')")
@RequiredArgsConstructor
public class OrderController {

  private static final String ORDER_TEXT = "/order";
  private final OrderRepository orderRepository;
  private final OrderService orderService;

  @GetMapping("/order/{id}")
  public ResponseEntity<Order> getOrder(@PathVariable Long id) {
    Order order = orderService.getOrder(id);
    Log4j2Config.logRequestInfo(CONSTANT_GET, CONSTANT_SECURE_URL + ORDER_TEXT,
        "Successfully consulted order",
        order.toString());
    return ResponseEntity.ok(order);
  }


  @PostMapping(value = "/order")
  public ResponseEntity<Order> createOrderAndOrderLine(@RequestBody OrderRequest orderRequest) {
    Order order = orderService.createOrder(orderRequest);
    Log4j2Config.logRequestInfo(CONSTANT_POST, CONSTANT_SECURE_URL + ORDER_TEXT,
        "Successfully inserted order",
        order.toString());
    return ResponseEntity.ok(order);
  }

  @PutMapping("/order")
  public ResponseEntity<Order> updateOrder(@RequestBody Order order) {
    Order result = orderService.saveOrder(order);
    Log4j2Config.logRequestInfo(CONSTANT_PUT, CONSTANT_SECURE_URL + ORDER_TEXT,
        "Successfully updated order",
        order.toString());
    return ResponseEntity.ok(result);
  }

  @DeleteMapping("/order/{id}")
  public ResponseEntity<Order> deleteOrder(@PathVariable Long id) {
    orderService.deleteOrder(id);
    Log4j2Config.logRequestInfo(CONSTANT_DELETE, CONSTANT_SECURE_URL + ORDER_TEXT,
        "Successfully deleted order with id " + id, null);
    return ResponseEntity.noContent().build();

  }


  @GetMapping("/orders")
  public List<Order> getOrders() {
    return orderRepository.findAll();
  }

}
