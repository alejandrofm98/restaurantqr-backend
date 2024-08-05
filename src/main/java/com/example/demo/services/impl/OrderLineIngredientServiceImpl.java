package com.example.demo.services.impl;

import com.example.demo.dto.request.OrderLineIngredientRequest;
import com.example.demo.dto.response.OrderLineRequest;
import com.example.demo.dto.request.mapper.OrderLineIngredientRequestMapper;
import com.example.demo.entity.OrderLineIngredient;
import com.example.demo.repository.OrderLineIngredientRepository;
import com.example.demo.services.OrderLineIngredientService;
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
