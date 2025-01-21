package com.example.demo.features.order.orderLine;

import com.example.demo.features.order.orderLineIngredient.OrderLineIngredientRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineRequest {
  Integer productId;
  Integer quantity;
  String observations;
  List<OrderLineIngredientRequest> orderLineIngredients;

}