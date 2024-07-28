package com.example.demo.controller;

import static com.example.demo.utils.Constants.CONSTANT_GET;
import static com.example.demo.utils.Constants.CONSTANT_ROL_ADMIN;
import static com.example.demo.utils.Constants.CONSTANT_ROL_OWNER;
import static com.example.demo.utils.Constants.CONSTANT_SECURE_URL;

import com.example.demo.config.Log4j2Config;
import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.IngredientRequest;
import com.example.demo.entity.Ingredient;
import com.example.demo.services.IngredientService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(CONSTANT_SECURE_URL)
@PreAuthorize("hasRole('" + CONSTANT_ROL_OWNER + "') or hasRole('" + CONSTANT_ROL_ADMIN + "')")
@RequiredArgsConstructor

public class IngredientController {

  private static final String INGREDIENT_TEXT = "/ingredient";
  private final IngredientService ingredientService;

  @GetMapping("/ingredient/{id}")
  public ResponseEntity<ApiResponse> getIngredient(@PathVariable Long id) {
    Ingredient ingredient = ingredientService.getIngredientById(id);
    Log4j2Config.logRequestInfo(CONSTANT_GET, CONSTANT_SECURE_URL + INGREDIENT_TEXT,
        "Successfully consulted ingredient by id " + id, ingredient.toString());
    ApiResponse apiResponse = ApiResponse.builder().response(ingredient).build();
    return ResponseEntity.ok(apiResponse);
  }

  @GetMapping("/ingredient/product/{productId}")
  public ResponseEntity<ApiResponse> getIngredientbyProductId(@PathVariable Long productId) {
    List<Ingredient> ingredient = ingredientService.getIngredientByProductId(productId);
    Log4j2Config.logRequestInfo(CONSTANT_GET, CONSTANT_SECURE_URL + INGREDIENT_TEXT,
        "Successfully consulted ingredients by productId " + productId, ingredient.toString());
    ApiResponse apiResponse = ApiResponse.builder().response(ingredient).build();
    return ResponseEntity.ok(apiResponse);
  }

  @PostMapping("/ingredient")
  public ResponseEntity<ApiResponse> addIngredient(
      @RequestPart("ingredient") IngredientRequest ingredientRequest,
      @RequestParam("image") MultipartFile file) {
    Ingredient ingredient = ingredientService.addIngredient(ingredientRequest, file);
    Log4j2Config.logRequestInfo(CONSTANT_GET, CONSTANT_SECURE_URL + INGREDIENT_TEXT,
        "Successfully inserted ingredient", ingredient.toString());
    ApiResponse apiResponse = ApiResponse.builder().response(ingredient).build();
    return ResponseEntity.ok(apiResponse);
  }

  @PutMapping("/ingredient")
  public ResponseEntity<ApiResponse> updateIngredient(
      @RequestPart("ingredient") IngredientRequest ingredientRequest,
      @RequestParam(value = "image", required = false) MultipartFile file) {
    Ingredient ingredientSaved = ingredientService.updateIngredient(ingredientRequest, file);
    Log4j2Config.logRequestInfo(CONSTANT_GET, CONSTANT_SECURE_URL + INGREDIENT_TEXT,
        "Successfully updated ingredient", ingredientSaved.toString());
    ApiResponse apiResponse = ApiResponse.builder().response(ingredientSaved).build();
    return ResponseEntity.ok(apiResponse);
  }

  @DeleteMapping("/ingredient/{id}")
  public ResponseEntity<ApiResponse> deleteIngredient(@PathVariable Long id) {
    Ingredient ingredient = ingredientService.getIngredientById(id);
    ingredientService.deleteIngredient(id);
    Log4j2Config.logRequestInfo(CONSTANT_GET, CONSTANT_SECURE_URL + INGREDIENT_TEXT,
        "Successfully deleted ingredient " + id, ingredient.toString());
    return ResponseEntity.noContent().build();

  }

  @GetMapping("/ingredients")
  public ResponseEntity<ApiResponse> getIngredients() {
    ApiResponse apiResponse = ApiResponse.builder().response(ingredientService.getIngredients())
        .build();

    return ResponseEntity.ok(apiResponse);
  }
}
