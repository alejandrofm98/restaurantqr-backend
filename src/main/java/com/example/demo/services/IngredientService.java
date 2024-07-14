package com.example.demo.services;

import com.example.demo.dto.IngredientRequest;
import com.example.demo.dto.mapper.IngredientMapper;
import com.example.demo.entity.Ingredient;
import com.example.demo.repository.IngredientRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientService {

  private final IngredientRepository ingredientRepository;

  public Ingredient getIngredientById(Long id) {
    Optional<Ingredient> ingredient = ingredientRepository.findById(id);
    return ingredient.orElseThrow(() -> new EntityNotFoundException("Ingredient not found"));
  }

  public Ingredient getIngredientByProductId(Long productId) {
    Optional<Ingredient> ingredient = ingredientRepository.findByProductId(productId);
    return ingredient.orElseThrow(() -> new EntityNotFoundException("Ingredient not found"));
  }

  public Ingredient addIngredient(IngredientRequest ingredientRequest) {
    Ingredient ingredient = IngredientMapper.INSTANCE.ingredientRequestToIngredient(
        ingredientRequest);
    return ingredientRepository.save(ingredient);

  }

}
