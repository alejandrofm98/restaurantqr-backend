package com.example.demo.features.business;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessRepository extends JpaRepository<Business, String> {
    boolean existsByBusinessUuid(String businessUuid);
    Optional<Business> findByBusinessUuid(String businessUuid);
}
