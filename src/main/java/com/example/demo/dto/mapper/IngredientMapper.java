package com.example.demo.dto.mapper;

import com.example.demo.dto.IngredientRequest;
import com.example.demo.entity.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IngredientMapper {

  IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

  IngredientRequest ingredientToIngredientRequest(Ingredient ingredient);
  Ingredient ingredientRequestToIngredient(IngredientRequest ingredientRequest);
}
