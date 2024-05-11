package com.example.demo.repository;

import com.example.demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {

  @Query("SELECT MAX(o.orderNumber)+1 FROM Order o where o.business.businessUuid =?1 ")
  Long findLastOrderNumberByBusinessUuid(String businessUuid);
}
