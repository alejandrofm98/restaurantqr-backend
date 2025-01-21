package com.example.demo.features.order.orderLineIngredient;

import com.example.demo.features.order.orderLine.OrderLineRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineIngredientServiceImpl implements OrderLineIngredientService {

  private final OrderLineIngredientRepository orderLineIngredientRepository;
  private final OrderLineIngredientRequestMapper orderLineIngredientMapper;

  public List<OrderLineIngredient> createOrderLineIngredients(
      OrderLineRequest orderLineRequest) {
    List<OrderLineIngredient> orderLineIngredients = new ArrayList<>();
    for (OrderLineIngredientRequest o : orderLineRequest.getOrderLineIngredients()) {
      orderLineIngredients.add(
          orderLineIngredientRepository.save(orderLineIngredientMapper.toEntity(o)));
    }

    return orderLineIngredients;
  }
}
