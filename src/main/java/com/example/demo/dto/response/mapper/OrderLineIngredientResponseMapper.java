package com.example.demo.dto.response.mapper;

import com.example.demo.dto.response.OrderLineIngredientResponse;
import com.example.demo.entity.OrderLineIngredient;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface OrderLineIngredientResponseMapper {

  OrderLineIngredient toEntity(OrderLineIngredientResponse orderLineIngredientResponse);

  @Mapping(target = "ingredientId", source = "ingredient.id")
  OrderLineIngredientResponse toDto(OrderLineIngredient orderLineIngredient);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  OrderLineIngredient partialUpdate(
      OrderLineIngredientResponse orderLineIngredientResponse,
      @MappingTarget OrderLineIngredient orderLineIngredient);
}
