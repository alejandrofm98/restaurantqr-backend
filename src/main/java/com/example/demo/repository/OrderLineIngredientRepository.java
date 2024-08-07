package com.example.demo.repository;

import com.example.demo.entity.OrderLineIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineIngredientRepository extends JpaRepository<OrderLineIngredient, Long> {

}
