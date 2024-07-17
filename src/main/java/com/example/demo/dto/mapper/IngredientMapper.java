package com.example.demo.dto.mapper;

import com.example.demo.dto.IngredientRequest;
import com.example.demo.entity.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IngredientMapper {


  @Mapping(source = "product.id", target = "productId")
  IngredientRequest toDto(Ingredient ingredient);


  @Mapping(source = "productId", target = "product.id")
  Ingredient toEntity(IngredientRequest ingredientRequest);


}





