package com.example.demo.services;

import com.example.demo.entity.Rol;
import java.util.List;

public interface RolService {

  Rol findById(Integer id);
  Rol findRolById(Integer id);
  List<Rol> findAll();
  Rol findByRolName(String rolName);

}
