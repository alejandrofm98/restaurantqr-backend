package com.example.demo.services;

import com.example.demo.entity.Rol;
import java.util.List;
import java.util.Optional;

public interface RolService {

  Rol findById(Integer id);

  List<Rol> findAll();
  Rol findByRolName(String rolName);

}
