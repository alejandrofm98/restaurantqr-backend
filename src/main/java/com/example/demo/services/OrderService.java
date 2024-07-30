package com.example.demo.services;

import com.example.demo.dto.OrderLineRequest;
import com.example.demo.dto.OrderRequest;
import com.example.demo.entity.Business;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderLine;
import com.example.demo.entity.OrderLineIngredient;
import com.example.demo.repository.BusinessRepository;
import com.example.demo.repository.OrderLineIngredientRepository;
import com.example.demo.repository.OrderLineRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
  private final BussinesService bussinesService;
  private final OrderLineIngredientService orderLineIngredientService;


  public Order getOrderByIdAndBusinessUuid(Long id, String businessUuid) {
    Business business = bussinesService.getBusinessById(businessUuid);
    Optional<Order> order = orderRepository.findByIdAndBusiness(id, business);
    return order.orElseThrow(() -> new EntityNotFoundException("Order not found"));
  }

  public List<Order> getOrdersByBusinessUuid(String businessUuid) {
    Business business = bussinesService.getBusinessById(businessUuid);
    List<Order> orders = orderRepository.findAllByBusiness(business);
    if (orders.isEmpty()) {
      throw new EntityNotFoundException("Orders not found");
    }
    return orders;
  }

  public Order saveOrder(Order order) {
    return orderRepository.save(order);
  }

  @Transactional
  public Order createOrder(OrderRequest orderRequest) {
    Order order = new Order();
    Business business = bussinesService.getBusinessById(orderRequest.getBusinessUuid());
    order.setBusiness(business);
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
      orderLine.setOrder(getOrderByIdAndBusinessUuid(orderId, orderRequest.getBusinessUuid()));
      orderLine.setProduct(productRepository.findById(o.getProductId())
          .orElseThrow(() -> new EntityNotFoundException("Product not found")));
      orderLine.setLineNumber(calculateNextLineNumber(orderId));
      orderLine.setQuantity(o.getQuantity());
      orderLine.setObservations(o.getObservations());
      orderLine.setOrderLineIngredients(
          orderLineIngredientService.createOrderLineIngredients(o));
      orderLines.add(orderLineRepository.save(orderLine));
    }
    return orderLines;
  }


  private Long calculateNextLineNumber(Long orderId) {
    return Optional.ofNullable(
        orderLineRepository.findLastOrderNumberByOrderId(orderId)).orElse(1L);


  }

  public void deleteOrder(Long id) {
    orderRepository.deleteById(id);
    if (orderRepository.existsById(id)) {
      throw new EntityExistsException("Order not deleted.");
    }


  }

}
