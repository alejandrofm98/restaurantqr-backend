package com.example.demo.dto;

import com.example.demo.entity.OrderLineIngredient;
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
