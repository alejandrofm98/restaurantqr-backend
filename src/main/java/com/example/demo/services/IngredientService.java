package com.example.demo.services;

import com.example.demo.dto.IngredientRequest;
import com.example.demo.dto.mapper.IngredientMapper;
import com.example.demo.entity.Ingredient;
import com.example.demo.repository.IngredientRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientService {

  private final IngredientRepository ingredientRepository;
  private static final String NOT_FOUND_TEXT = "Ingredient not found";

  public Ingredient getIngredientById(Long id) {
    Optional<Ingredient> ingredient = ingredientRepository.findById(id);
    return ingredient.orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_TEXT));
  }

  public Ingredient getIngredientByProductId(Long productId) {
    Optional<Ingredient> ingredient = ingredientRepository.findByProductId(productId);
    return ingredient.orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_TEXT));
  }

  public Ingredient addIngredient(IngredientRequest ingredientRequest) {
    Ingredient ingredient = IngredientMapper.INSTANCE.ingredientRequestToIngredient(
        ingredientRequest);
    return ingredientRepository.save(ingredient);
  }

  public Ingredient updateIngredient(Ingredient ingredient) {
    ingredientRepository.findById(ingredient.getId())
        .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_TEXT));
    return ingredientRepository.save(ingredient);
  }

  public void deleteIngredient(Long id) {
    ingredientRepository.deleteById(id);
    if (ingredientRepository.existsById(id)) {
      throw new EntityExistsException("Ingredient not deleted.");

    }
  }

  public List<Ingredient> getOrders() {
    List<Ingredient> ingredients = ingredientRepository.findAll();
    if (ingredients.isEmpty()) {
      throw new EntityNotFoundException("Orders not found");
    }
    return ingredients;
  }

}
