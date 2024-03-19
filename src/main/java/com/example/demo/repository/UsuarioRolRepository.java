package com.example.demo.repository;


import com.example.demo.entity.Rol;
import com.example.demo.entity.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol,Integer> {
    UsuarioRol findByIdUsuario(Integer idUsuario);
}
