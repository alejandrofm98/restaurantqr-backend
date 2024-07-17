package com.example.demo.repository;

import com.example.demo.entity.Ingredient;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
  @Query("SELECT i FROM Ingredient i INNER JOIN Product  p ON i.product.id = p.id "
      + "WHERE i.product.id=?1 AND p.business.businessUuid = ?2")
  Optional<Ingredient> findByProductIdAndBusinnesUuid(Long productId, String businnesUuid);

  @Query("SELECT i FROM Ingredient i INNER JOIN Product p ON i.product.id = p.id "
      + "WHERE i.id = ?1 and p.business.businessUuid = ?2 ")
  Optional<Ingredient> findByIdAndBusinnesUUid(Long id, String businessUuid);

  @Query("SELECT i FROM Ingredient  i INNER JOIN Product p ON i.product.id = p.id "
      + "WHERE p.business.businessUuid = ?1")
  List<Ingredient> findAllByBusinessUuid(String businessUuid);
}
