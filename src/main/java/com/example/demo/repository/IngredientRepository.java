package com.example.demo.repository;

import com.example.demo.entity.Ingredient;
import com.example.demo.entity.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
  Optional<Ingredient> findByProductId(Product productId);
  @Query("SELECT i.IngredientBuilder FROM Ingredient i INNER JOIN Product p ON i.product.id = p.id "
      + "where i.id = ?1 and p.business.businessUuid = ?2 ")
  Optional<Ingredient> findByIdAndBusinnes(Long id, String businessUuid);

}
