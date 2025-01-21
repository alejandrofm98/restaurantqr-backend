package com.example.demo.features.order.orderLine;

import com.example.demo.features.order.order.Order;
import com.example.demo.features.order.order.OrderRequest;
import java.util.List;

public interface OrderLineService {
  List<OrderLine> createOrderLine(OrderRequest orderRequest, Order parentOrder);

}
