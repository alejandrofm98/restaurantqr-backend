package com.example.demo.repository;

import com.example.demo.entity.Ingredient;
import com.example.demo.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
  Optional<Ingredient> findByProductId(Product productId);
}
