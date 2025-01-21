package com.example.demo.features.order.orderLine;

import com.example.demo.features.order.orderLineIngredient.OrderLineIngredientResponseMapper;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING,
    uses = OrderLineIngredientResponseMapper.class)
public interface OrderLineResponseMapper {

  OrderLine toEntity(OrderLineResponse orderLineResponse);

  @AfterMapping
  default void linkOrderLineIngredients(@MappingTarget OrderLine orderLine) {
    orderLine.getOrderLineIngredients()
        .forEach(orderLineIngredient -> orderLineIngredient.setOrderLine(orderLine));
  }

  @Mapping(target = "orderId", source = "order.id")
  @Mapping(target = "productId", source = "product.id")
  OrderLineResponse toDto(OrderLine orderLine);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  OrderLine partialUpdate(
      OrderLineResponse orderLineResponse, @MappingTarget OrderLine orderLine);
}
