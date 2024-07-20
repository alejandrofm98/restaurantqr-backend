package com.example.demo.services;

import com.example.demo.dto.IngredientRequest;
import com.example.demo.dto.mapper.IngredientMapper;
import com.example.demo.entity.Ingredient;
import com.example.demo.repository.IngredientRepository;
import com.example.demo.utils.Images;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class IngredientService {

  private static final String IMAGES_INGREDIENTS_FOLDER = "images/ingredients/";
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

  public List<Ingredient> getIngredientByProductId(Long productId) {
    return ingredientRepository.findByProductIdAndBusinnesUuid(productId,
        auxService.getBussinesUUid());
  }

  public Ingredient addIngredient(IngredientRequest ingredientRequest, MultipartFile file) {

    Ingredient ingredient = ingredientMapper.toEntity(ingredientRequest);
    try {
      if (productService.getProductById(ingredient.getProduct().getId()) != null) {
        Images images = new Images(file, ingredientRequest.getImage(),
            IMAGES_INGREDIENTS_FOLDER + auxService.getBussinesUUid());
        ingredient.setImage(images.checkImg());
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        images.writeImg();
        return savedIngredient;
      }
    } catch (NoSuchElementException exception) {
      throw new EntityNotFoundException(PRODUCT_NOT_FOUND);
    }
    return null;
  }

  public Ingredient updateIngredient(IngredientRequest ingredientRequest, MultipartFile file) {
    Ingredient ingredient = ingredientMapper.toEntity(ingredientRequest);

    if (ingredientRepository.findByIdAndBusinnesUUid(ingredient.getId(),
        auxService.getBussinesUUid()).isEmpty()) {
      throw new EntityNotFoundException(INGREDIENT_NOT_FOUND);
    }

    if (ingredientRepository.findByProductIdAndBusinnesUuid(ingredient.getProduct().getId(),
        auxService.getBussinesUUid()).isEmpty()) {
      throw new EntityNotFoundException("The product doesn't exist in this Bussiness");
    }
    Images images = null;
    if (file != null) { // Comprobamos y seteamos la imagen con la ruta real
      images = new Images(file, ingredientRequest.getImage(),
          IMAGES_INGREDIENTS_FOLDER + auxService.getBussinesUUid());
      ingredient.setImage(images.checkImg());
    }
    Ingredient savedIngredient = ingredientRepository.save(ingredient);
    if (images != null) { // Escribimos la imagen una vez se ha guardado en BD.
      images.writeImg();
    }
    return savedIngredient;
  }

  public void deleteIngredient(Long id) {
    Optional<Ingredient> ingredient = ingredientRepository.findByIdAndBusinnesUUid(id,
        auxService.getBussinesUUid());
    if (ingredient.isPresent()) {
      ingredientRepository.deleteById(id);
      Images.deleteImg(ingredient.get().getImage());
    }
    if (ingredientRepository.existsById(id)) {
      throw new EntityExistsException("Ingredient not deleted.");
    }
  }

  public List<Ingredient> getIngredients() {
    List<Ingredient> ingredients = ingredientRepository.findAllByBusinessUuid(
        auxService.getBussinesUUid());
    if (ingredients.isEmpty()) {
      throw new EntityNotFoundException("Ingredients not found");
    }
    return ingredients;
  }


}
