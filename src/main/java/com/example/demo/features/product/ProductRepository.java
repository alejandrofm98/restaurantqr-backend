package com.example.demo.features.product;

import com.example.demo.features.business.Business;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product,Integer> {
    List<Product> findByBusiness(Business business);

    @Query("SELECT p  FROM Product p "
        + "where p.id = ?1 and p.business.businessUuid = ?2 ")
    Optional<Product> findByIdAndBusinnesUuid(Long id, String businessUuid);
}
