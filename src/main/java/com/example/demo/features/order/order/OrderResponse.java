package com.example.demo.features.order.order;

import com.example.demo.features.order.orderLine.OrderLineResponse;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Value;

/**
 * DTO for {@link Order}
 */
@Value
public class OrderResponse implements Serializable {

  Long id;
  Long orderNumber;
  String businessBusinessUuid;
  LocalDateTime startDate;
  LocalDateTime endDate;
  List<OrderLineResponse> orderLines;
}
