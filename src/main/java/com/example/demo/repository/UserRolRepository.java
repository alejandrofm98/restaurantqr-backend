package com.example.demo.repository;


import com.example.demo.entity.UserRol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRolRepository extends JpaRepository<UserRol,Integer> {
    UserRol findByIdUser(Integer idUser);
}
