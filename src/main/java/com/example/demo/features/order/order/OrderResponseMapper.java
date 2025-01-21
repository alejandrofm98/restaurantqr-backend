package com.example.demo.features.order.order;

import com.example.demo.features.order.orderLine.OrderLineResponseMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING,
    uses = OrderLineResponseMapper.class)

public interface OrderResponseMapper {

  Order toEntity(OrderResponse orderResponse);

  @AfterMapping
  default void linkOrderLines(@MappingTarget Order order) {
    order.getOrderLines().forEach(orderLine -> orderLine.setOrder(order));
  }

  @Mapping(source = "business.businessUuid", target = "businessBusinessUuid")
  OrderResponse toDto(Order order);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Order partialUpdate(
      OrderResponse orderResponse, @MappingTarget Order order);
}
