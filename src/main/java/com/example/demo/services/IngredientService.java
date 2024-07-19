package com.example.demo.services;

import com.example.demo.dto.IngredientRequest;
import com.example.demo.dto.mapper.IngredientMapper;
import com.example.demo.entity.Ingredient;
import com.example.demo.repository.IngredientRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IngredientService {

  private final IngredientRepository ingredientRepository;
  private final IngredientMapper ingredientMapper;
  private final ProductService productService;
  private static final String INGREDIENT_NOT_FOUND = "Ingredient not found";
  private static final String PRODUCT_NOT_FOUND = "";
  private final AuxService auxService;


  public Ingredient getIngredientById(Long id) {
    Optional<Ingredient> ingredient = ingredientRepository.findByIdAndBusinnesUUid(id,
        auxService.getBussinesUUid());
    return ingredient.orElseThrow(() -> new EntityNotFoundException(INGREDIENT_NOT_FOUND));
  }

  public Ingredient getIngredientByProductId(Long productId) {
    Optional<Ingredient> ingredient = ingredientRepository.findByProductIdAndBusinnesUuid(productId,
        auxService.getBussinesUUid());
    return ingredient.orElseThrow(() -> new EntityNotFoundException(INGREDIENT_NOT_FOUND));
  }

  public Ingredient addIngredient(IngredientRequest ingredientRequest) {

    Ingredient ingredient = ingredientMapper.toEntity(
        ingredientRequest);
    try {
      if (productService.getProductById(ingredient.getProduct().getId()) != null) {
        return ingredientRepository.save(ingredient);
      }
    } catch (NoSuchElementException exception) {
      throw new EntityNotFoundException(PRODUCT_NOT_FOUND);
    }
    return null;
  }

  public Ingredient updateIngredient(IngredientRequest ingredientRequest) {
    Ingredient ingredient = ingredientMapper.toEntity(
        ingredientRequest);

    if (ingredientRepository.findByIdAndBusinnesUUid(ingredient.getId(),
        auxService.getBussinesUUid()).isEmpty()) {
      throw new EntityNotFoundException(INGREDIENT_NOT_FOUND);
    }

    if (ingredientRepository.findByProductIdAndBusinnesUuid(ingredient.getProduct().getId(),
        auxService.getBussinesUUid()).isEmpty()) {
      throw new EntityNotFoundException("The product doesn't exist in this Bussiness");
    }

    return ingredientRepository.save(ingredient);
  }

  public void deleteIngredient(Long id) {

    if (ingredientRepository.findByIdAndBusinnesUUid(id, auxService.getBussinesUUid())
        .isPresent()) {
      ingredientRepository.deleteById(id);
    }
    if (ingredientRepository.existsById(id)) {
      throw new EntityExistsException("Ingredient not deleted.");
    }
  }

  public List<Ingredient> getOrders() {
    List<Ingredient> ingredients = ingredientRepository.findAllByBusinessUuid(
        auxService.getBussinesUUid());
    if (ingredients.isEmpty()) {
      throw new EntityNotFoundException("Orders not found");
    }
    return ingredients;
  }


}
