package com.example.demo.repository;

import com.example.demo.entity.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
  @Query("SELECT MAX(o.lineNumber)+1 FROM OrderLine o where o.order.id =?1 ")
  Long findLastOrderNumberByOrderId(Long orderId);
}
