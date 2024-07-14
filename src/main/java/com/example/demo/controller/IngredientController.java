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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        "Successfully consulted ingredient by id " + id,
        ingredient.toString());
    ApiResponse apiResponse = ApiResponse.builder()
        .response(ingredient)
        .build();
    return ResponseEntity.ok(apiResponse);
  }

  @GetMapping("/ingredient/{productId}")
  public ResponseEntity<ApiResponse> getIngredientbyProductId(@PathVariable Long productId) {
    Ingredient ingredient = ingredientService.getIngredientByProductId(productId);
    Log4j2Config.logRequestInfo(CONSTANT_GET, CONSTANT_SECURE_URL + INGREDIENT_TEXT,
        "Successfully consulted ingredient by productId " + productId,
        ingredient.toString());
    ApiResponse apiResponse = ApiResponse.builder()
        .response(ingredient)
        .build();
    return ResponseEntity.ok(apiResponse);
  }

  @PostMapping("/ingredient")
  public ResponseEntity<ApiResponse> addIngredient(@RequestBody IngredientRequest ingredientRequest) {

  }

}
