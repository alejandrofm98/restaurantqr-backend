package com.example.demo.services;

import com.example.demo.dto.request.IngredientRequest;
import com.example.demo.entity.Ingredient;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface IngredientService {

  Ingredient getIngredientById(Long id);

  List<Ingredient> getIngredientByProductId(Long productId);

  Ingredient addIngredient(IngredientRequest ingredientRequest, MultipartFile file);

  Ingredient updateIngredient(IngredientRequest ingredientRequest, MultipartFile file);

  void deleteIngredient(Long id);

  List<Ingredient> getIngredients();
}
