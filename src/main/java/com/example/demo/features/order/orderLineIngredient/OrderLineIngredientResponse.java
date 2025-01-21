package com.example.demo.features.order.orderLineIngredient;

import java.io.Serializable;
import lombok.Value;

/**
 * DTO for {@link OrderLineIngredient}
 */
@Value
public class OrderLineIngredientResponse implements Serializable {

  Long id;
  Long ingredientId;
  int quantity;
}
