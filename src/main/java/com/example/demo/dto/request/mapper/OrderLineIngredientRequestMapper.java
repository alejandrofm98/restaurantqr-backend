package com.example.demo.dto.request.mapper;

import com.example.demo.dto.request.OrderLineIngredientRequest;
import com.example.demo.entity.OrderLineIngredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderLineIngredientRequestMapper {


  OrderLineIngredientRequestMapper INSTANCE = Mappers.getMapper(OrderLineIngredientRequestMapper.class);

  @Mapping(source = "ingredient.id", target = "ingredientId")
  @Mapping(source = "orderLine.id", target = "orderLineId")
  OrderLineIngredientRequest toDto(OrderLineIngredient orderLineIngredient);


  @Mapping(source = "ingredientId", target = "ingredient.id")
  @Mapping(source = "orderLineId", target = "orderLine.id")
  OrderLineIngredient toEntity(OrderLineIngredientRequest orderLineIngredientRequest);

}
