package com.example.demo.controller;

import com.example.demo.config.Log4j2Config;
import com.example.demo.entity.Product;
import com.example.demo.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.example.demo.utils.Constants.*;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //Insertar productos + foto
    @PostMapping(value = "products", consumes = {"multipart/form-data"})
    public Product uploadImage(@RequestParam("image") MultipartFile file, @RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("price") String price, @RequestParam("category") Integer category, @RequestParam("status") Integer status, @RequestParam("idempresa") Integer idempresa) throws IOException {
        Product uploadedProduct = productService.uploadImage(file, name, description, price, category, status, idempresa);
        Log4j2Config.logRequestInfo(CONSTANT_POST, CONSTANT_SECURE_URL + "/products",
                "Successfully inserted product",
                uploadedProduct.toString());

        return uploadedProduct;
    }

    //Updatear productos + foto
    @PutMapping("products/{id}")
    public ResponseEntity<Product> updateProductWithImage(
            @PathVariable Integer id,
            @RequestParam(value = "image", required = false) MultipartFile file,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String price,
            @RequestParam Integer category,
            @RequestParam Integer status,
            @RequestParam Integer idempresa
    ) {
        try {
            Product updatedProduct = productService.updateProductWithImage(id, file, name, description, price, category, status, idempresa);
            Log4j2Config.logRequestInfo(CONSTANT_PUT, CONSTANT_SECURE_URL + "/products/{id}",
                    "Successfully updated product",
                    updatedProduct.toString()
            );
            return ResponseEntity.ok(updatedProduct);
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

            Log4j2Config.logRequestInfo(CONSTANT_DELETE, CONSTANT_SECURE_URL + "/products/{id}",
                    "Successfully deleted product",
                    deletedProduct.toString());

            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException | IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}