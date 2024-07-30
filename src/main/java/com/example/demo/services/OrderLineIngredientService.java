package com.example.demo.services;

import com.example.demo.dto.OrderLineIngredientRequest;
import com.example.demo.dto.OrderLineRequest;
import com.example.demo.dto.mapper.OrderLineIngredientMapper;
import com.example.demo.entity.OrderLineIngredient;
import com.example.demo.repository.OrderLineIngredientRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderLineIngredientService {

  private final OrderLineIngredientRepository orderLineIngredientRepository;
  private final OrderLineIngredientMapper orderLineIngredientMapper;

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
