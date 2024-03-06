package com.example.demo.repository;


import com.example.demo.entity.Usuario_rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Usuario_rolRepository extends JpaRepository<Usuario_rol,Integer> {

    Usuario_rol save(Usuario_rol usuarioRol);

}
