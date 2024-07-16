package com.example.demo.services;

import com.example.demo.dto.IngredientRequest;
import com.example.demo.dto.mapper.IngredientMapper;
import com.example.demo.entity.Ingredient;
import com.example.demo.entity.Product;
import com.example.demo.entity.User;
import com.example.demo.jwt.JwtService;
import com.example.demo.repository.IngredientRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
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
  private static final String NOT_FOUND_TEXT = "Ingredient not found";
  private final JwtService jwtService;
  private final HttpServletRequest request;
  private final UserService userService;
  private final BussinesService bussinesService;


  public Ingredient getIngredientById(Long id) {
    User user = userService.findUserbyUser(jwtService.getUsername(request));
    Optional<Ingredient> ingredient = ingredientRepository.findByIdAndBusinnes(id, user.getBusinessUuid());
    return ingredient.orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_TEXT));
  }

  public Ingredient getIngredientByProductId(Long productId) {
    Product product = new Product();
    product.setId(productId);
    Optional<Ingredient> ingredient = ingredientRepository.findByProductId(product);
    return ingredient.orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_TEXT));
  }

  public Ingredient addIngredient(IngredientRequest ingredientRequest) {

    Ingredient ingredient = ingredientMapper.toEntity(
        ingredientRequest);
    try {
//      productService.getProductById(Math.toIntExact(ingredient.getProduct().getId()));
    } catch (NoSuchElementException exception) {
      throw new EntityNotFoundException("Product not found");
    }
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
