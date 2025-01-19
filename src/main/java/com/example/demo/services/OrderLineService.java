package com.example.demo.services;

import com.example.demo.dto.request.OrderRequest;
import com.example.demo.entity.Order;
import com.example.demo.entity.OrderLine;
import java.util.List;

public interface OrderLineService {
  List<OrderLine> createOrderLine(OrderRequest orderRequest, Order parentOrder);

}
