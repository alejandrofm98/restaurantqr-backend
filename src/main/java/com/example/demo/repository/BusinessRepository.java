package com.example.demo.repository;

import com.example.demo.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<Business, String> {
    boolean existsByBusinessUuid(String businessUuid);
    Optional<Business> findByBusinessUuid(String businessUuid);
}
