package com.example.demo.features.rol;

import java.util.List;

public interface RolService {

  Rol findById(Integer id);
  Rol findRolById(Integer id);
  List<Rol> findAll();
  Rol findByRolName(String rolName);

}
