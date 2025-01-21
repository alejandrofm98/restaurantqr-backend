package com.example.demo.features.order.orderLineIngredient;

import com.example.demo.features.order.orderLine.OrderLineRequest;
import java.util.List;

public interface OrderLineIngredientService {

  List<OrderLineIngredient> createOrderLineIngredients(OrderLineRequest orderLineRequest);
}
