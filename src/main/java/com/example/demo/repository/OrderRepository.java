package com.example.demo.repository;

import com.example.demo.entity.Business;
import com.example.demo.entity.Order;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {

  @Query("SELECT MAX(o.orderNumber)+1 FROM Order o where o.business.businessUuid =?1 ")
  Long findLastOrderNumberByBusinessUuid(String businessUuid);

  Optional<Order> findByIdAndBusiness(Long id, Business business);
  List<Order> findAllByBusiness(Business business);
}
