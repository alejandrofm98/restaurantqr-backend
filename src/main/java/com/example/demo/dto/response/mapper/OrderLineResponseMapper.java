package com.example.demo.dto.response.mapper;

import com.example.demo.dto.response.OrderLineResponse;
import com.example.demo.entity.OrderLine;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface OrderLineResponseMapper {

  OrderLine toEntity(OrderLineResponse orderLineResponse);

  @AfterMapping
  default void linkOrderLineIngredients(@MappingTarget OrderLine orderLine) {
    orderLine.getOrderLineIngredients()
        .forEach(orderLineIngredient -> orderLineIngredient.setOrderLine(orderLine));
  }

  OrderLineResponse toDto(OrderLine orderLine);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  OrderLine partialUpdate(
      OrderLineResponse orderLineResponse, @MappingTarget OrderLine orderLine);
}
