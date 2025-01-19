package com.example.demo.services.impl;

import com.example.demo.dto.request.OrderRequest;
import com.example.demo.dto.response.OrderResponse;
import com.example.demo.dto.response.mapper.OrderResponseMapper;
import com.example.demo.entity.Business;
import com.example.demo.entity.Order;
import com.example.demo.repository.OrderRepository;
import com.example.demo.services.BussinesService;
import com.example.demo.services.OrderLineService;
import com.example.demo.services.OrderService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final BussinesService bussinesService;
  private final OrderResponseMapper orderResponseMapper;
  private final OrderLineService orderLineService;


  public OrderResponse getOrderByIdAndBusinessUuidDTO(Long id, String businessUuid) {
    Order order = getOrderByIdAndBusinessUuid(id, businessUuid);
    return orderResponseMapper.toDto(order);
  }
  public Order getOrderByIdAndBusinessUuid(Long id, String businessUuid) {
    Business business = bussinesService.findBusinessById(businessUuid);
    Optional<Order> order = orderRepository.findByIdAndBusiness(id, business);
    if (order.isEmpty()) {
      throw new EntityNotFoundException("Order not found");
    }

    return order.get();
  }

  public List<OrderResponse> getOrdersByBusinessUuid(String businessUuid) {
    Business business = bussinesService.findBusinessById(businessUuid);
    List<Order> orders = orderRepository.findAllByBusiness(business);
    if (orders.isEmpty()) {
      throw new EntityNotFoundException("Orders not found");
    }
    return orders.stream().map(orderResponseMapper::toDto).toList();
  }

  public OrderResponse saveOrder(Order order) {

    Order orderSaved = orderRepository.save(order);
    return orderResponseMapper.toDto(orderSaved);
  }

  @Transactional
  public OrderResponse createOrder(OrderRequest orderRequest) {
    Order order = new Order();
    Business business = bussinesService.findBusinessById(orderRequest.getBusinessUuid());
    order.setBusiness(business);
    order.setOrderNumber(this.calculateNextOrderId(orderRequest.getBusinessUuid()));
    Order resultado = orderRepository.save(order);
    resultado.setOrderLines(orderLineService.createOrderLine(orderRequest, resultado));
    return orderResponseMapper.toDto(resultado);
  }

  private Long calculateNextOrderId(String businessUuid) {
    return Optional.ofNullable(
        orderRepository.findLastOrderNumberByBusinessUuid(businessUuid)).orElse(1L);

  }



  public void deleteOrder(Long id) {
    orderRepository.deleteById(id);
    if (orderRepository.existsById(id)) {
      throw new EntityExistsException("Order not deleted.");
    }


  }

}
