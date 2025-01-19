package com.example.demo.services;

import com.example.demo.dto.request.OrderRequest;
import com.example.demo.dto.response.OrderResponse;
import com.example.demo.entity.Order;
import java.util.List;

public interface OrderService {

  OrderResponse getOrderByIdAndBusinessUuidDTO(Long id, String businessUuid);
  Order getOrderByIdAndBusinessUuid(Long id, String businessUuid);
  List<OrderResponse> getOrdersByBusinessUuid(String businessUuid);
  OrderResponse saveOrder(Order order);
  OrderResponse createOrder(OrderRequest orderRequest);
  void deleteOrder(Long id);
}
