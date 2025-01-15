package com.example.demo.repository;

import com.example.demo.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol,Integer> {
    Optional<Rol> findByRole(String rol);
}
