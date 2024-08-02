package com.example.demo.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Value;

/**
 * DTO for {@link com.example.demo.entity.Order}
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
