package com.example.demo.services.impl;

import com.example.demo.dto.request.OrderLineIngredientRequest;
import com.example.demo.dto.request.OrderLineRequest;
import com.example.demo.dto.request.OrderRequest;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderLine;
import com.example.demo.repository.OrderLineRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.services.OrderLineIngredientService;
import com.example.demo.services.OrderLineService;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineServiceImpl implements OrderLineService {

  private final OrderLineRepository orderLineRepository;
  private final OrderLineIngredientService orderLineIngredientService;
  private final ProductRepository productRepository;

  public List<OrderLine> createOrderLine(OrderRequest orderRequest, Order parentOrder) {
    List<OrderLine> orderLines = new ArrayList<>();
    for (OrderLineRequest o : orderRequest.getOrderLines()) {
      OrderLine orderLine = new OrderLine();
      orderLine.setOrder(parentOrder);
      orderLine.setProduct(productRepository.findById(o.getProductId())
          .orElseThrow(() -> new EntityNotFoundException("Product not found")));
      orderLine.setLineNumber(calculateNextLineNumber(parentOrder.getId()));
      orderLine.setQuantity(o.getQuantity());
      orderLine.setObservations(o.getObservations());

      OrderLine orderLineSaved = orderLineRepository.save(orderLine);

      // Permite que no sea obligatorio tener ingredientes en una orden
      if (o.getOrderLineIngredients() != null) {

        //Asignamos el id de la orderLine guardada para poder asociarala en orderLineIngredient
        for (OrderLineIngredientRequest orderLineIngredientModified : o.getOrderLineIngredients()) {
          orderLineIngredientModified.setOrderLineId(orderLineSaved.getId());
        }
        orderLine.setOrderLineIngredients(
            orderLineIngredientService.createOrderLineIngredients(o));
        orderLineSaved = orderLineRepository.save(orderLineSaved);
      }

      orderLines.add(orderLineSaved);


    }
    return orderLines;
  }


  private Long calculateNextLineNumber(Long orderId) {
    return Optional.ofNullable(
        orderLineRepository.findLastOrderNumberByOrderId(orderId)).orElse(1L);


  }
}
