package com.example.demo.dto.response.mapper;

import com.example.demo.dto.response.OrderResponse;
import com.example.demo.entity.Order;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface OrderResponseMapper {

  Order toEntity(OrderResponse orderResponse);

  @AfterMapping
  default void linkOrderLines(@MappingTarget Order order) {
    order.getOrderLines().forEach(orderLine -> orderLine.setOrder(order));
  }

  OrderResponse toDto(Order order);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Order partialUpdate(
      OrderResponse orderResponse, @MappingTarget Order order);
}
