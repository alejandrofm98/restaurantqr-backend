package com.example.demo.features.order.order;

import java.util.List;

public interface OrderService {

  OrderResponse getOrderByIdAndBusinessUuidDTO(Long id, String businessUuid);
  Order getOrderByIdAndBusinessUuid(Long id, String businessUuid);
  List<OrderResponse> getOrdersByBusinessUuid(String businessUuid);
  OrderResponse saveOrder(Order order);
  OrderResponse createOrder(OrderRequest orderRequest);
  void deleteOrder(Long id);
}
