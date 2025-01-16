package com.example.demo.controller;

import static com.example.demo.utils.Constants.CONSTANT_DELETE;
import static com.example.demo.utils.Constants.CONSTANT_POST;
import static com.example.demo.utils.Constants.CONSTANT_PUT;
import static com.example.demo.utils.Constants.CONSTANT_ROL_ADMIN;
import static com.example.demo.utils.Constants.CONSTANT_ROL_OWNER;
import static com.example.demo.utils.Constants.CONSTANT_SECURE_URL;

import com.example.demo.config.Log4j2Config;
import com.example.demo.dto.request.ProductRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.entity.Product;
import com.example.demo.services.AuxService;
import com.example.demo.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
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

public class ProductController {

  private final ProductService productService;
  private final AuxService auxService;

  //Insertar productos + foto
  @PostMapping(value = "products", consumes = {"multipart/form-data"})
  public ResponseEntity<ApiResponse> uploadImage(@RequestParam("image") MultipartFile file,
     @RequestPart("product")ProductRequest productRequest)
      throws IOException {
    Product uploadedProduct = productService.insertProductWithImage(file, productRequest);
    Log4j2Config.logRequestInfo(CONSTANT_POST, CONSTANT_SECURE_URL + "/products",
        "Successfully inserted product",
        uploadedProduct.toString());

    ApiResponse apiResponse = ApiResponse.builder()
        .response(uploadedProduct)
        .build();

    return ResponseEntity.ok(apiResponse);
  }

  //Updatear productos + foto
  @PutMapping("products/{id}")
  public ResponseEntity<ApiResponse> updateProductWithImage(
      @PathVariable Integer id,
      @RequestParam(value = "image", required = false) MultipartFile file,
      @RequestPart("product") ProductRequest productRequest) {
    try {
      Product updatedProduct = productService.updateProductWithImage(id, file, productRequest);
      Log4j2Config.logRequestInfo(CONSTANT_PUT, CONSTANT_SECURE_URL + "/products/" + id,
          "Successfully updated product",
          updatedProduct.toString()
      );
      ApiResponse apiResponse = ApiResponse.builder()
          .response(updatedProduct)
          .build();

      return ResponseEntity.ok(apiResponse);
    } catch (IOException | EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  //Delete productos + foto
  @DeleteMapping("products/{id}")
  public ResponseEntity<Void> deleteProductAndImage(@PathVariable Integer id) {
    try {
      // Eliminar el producto y obtener el producto eliminado
      Product deletedProduct = productService.deleteProductAndImage(id);

      Log4j2Config.logRequestInfo(CONSTANT_DELETE, CONSTANT_SECURE_URL + "/products/" + id,
          "Successfully deleted product",
          deletedProduct.toString());
      return ResponseEntity.noContent().build();
    } catch (EntityNotFoundException | IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }


}
