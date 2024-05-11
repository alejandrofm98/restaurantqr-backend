package com.example.demo.services;

import com.example.demo.dto.OrderLineRequest;
import com.example.demo.dto.OrderRequest;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderLine;
import com.example.demo.repository.BusinessRepository;
import com.example.demo.repository.OrderLineRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final OrderLineRepository orderLineRepository;
  private final ProductRepository productRepository;
  private final BusinessRepository businessRepository;

  public Order getOrder(Long id) {
    Optional<Order> order = orderRepository.findById(id);
    return order.orElseThrow(() -> new RuntimeException("Order not found"));
  }

  public Order createOrder(OrderRequest orderRequest) {
    Order order = new Order();
    order.setBusiness(businessRepository.findById(orderRequest.getBusinessUuid()).get());
    order.setOrderNumber(this.calculateNextOrderId(orderRequest.getBusinessUuid()));
    Order resultado = orderRepository.save(order);
    resultado.setOrderLines(createOrderLine(orderRequest, resultado.getId()));

    return resultado;
  }

  private Long calculateNextOrderId(String businessUuid) {
    return Optional.ofNullable(
        orderRepository.findLastOrderNumberByBusinessUuid(businessUuid)).orElse(1L);

  }

  public List<OrderLine> createOrderLine(OrderRequest orderRequest, Long orderId) {
    List<OrderLine> orderLines = new ArrayList<>();
    for (OrderLineRequest o : orderRequest.getOrderLines()) {
      OrderLine orderLine = new OrderLine();
      Order order = orderRepository.findById(orderId).get();
      orderLine.setOrder(order);
      orderLine.setProduct(productRepository.findById(o.getProductId()).get());
      orderLine.setLineNumber(calculateNextLineNumber(orderId));
      orderLine.setQuantity(o.getQuantity());
      orderLine.setObservations(o.getObservations());
      orderLines.add(orderLineRepository.save(orderLine));
    }
    return orderLines;
  }

  private Long calculateNextLineNumber(Long orderId) {
    return Optional.ofNullable(
        orderLineRepository.findLastOrderNumberByOrderId(orderId)).orElse(1L);


  }

  public void deleteOrder(Long id){
     orderRepository.deleteById(id);
  }
}
