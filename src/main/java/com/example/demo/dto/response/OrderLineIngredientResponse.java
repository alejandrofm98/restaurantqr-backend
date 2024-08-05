package com.example.demo.dto.response;

import com.example.demo.entity.OrderLineIngredient;
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
