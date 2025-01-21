package com.example.demo.features.order.order;

import com.example.demo.features.order.orderLine.OrderLineRequest;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
  String businessUuid;
  List<OrderLineRequest> orderLines;
}
