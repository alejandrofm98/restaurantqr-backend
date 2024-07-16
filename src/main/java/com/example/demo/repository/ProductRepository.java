package com.example.demo.repository;

import com.example.demo.entity.Business;
import com.example.demo.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByBusiness(Business business);
    @Query("SELECT p.ProductBuilder  FROM Product p INNER JOIN Product p ON i.product.id = p.id "
        + "where i.id = ?1 and p.business.businessUuid = ?2 ")
    Optional<Product> findByIdAndBusinnes(Long id, String businessUuid);
}
