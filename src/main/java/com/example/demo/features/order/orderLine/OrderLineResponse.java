package com.example.demo.features.order.orderLine;

import com.example.demo.features.order.orderLineIngredient.OrderLineIngredientResponse;
import java.io.Serializable;
import java.util.List;
import lombok.Value;

/**
 * DTO for {@link OrderLine}
 */
@Value
public class OrderLineResponse implements Serializable {

  Long id;
  Long lineNumber;
  Integer quantity;
  String observations;
  Long orderId;
  Long productId;
  List<OrderLineIngredientResponse> orderLineIngredients;
}
