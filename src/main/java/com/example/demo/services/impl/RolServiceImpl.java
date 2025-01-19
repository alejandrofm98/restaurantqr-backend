package com.example.demo.services.impl;

import com.example.demo.entity.Rol;
import com.example.demo.repository.RolRepository;
import com.example.demo.services.RolService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RolServiceImpl implements RolService {

  private final RolRepository rolRepository;

  public RolServiceImpl(RolRepository rolRepository) {
    this.rolRepository = rolRepository;
  }

  @Override
  public Rol findById(Integer id) {
    return rolRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Rol id " + id + " not found"));
  }

  @Override
  public Rol findRolById(Integer id) {
    return rolRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Rol id " + id + " not found"));
  }

  public List<Rol> findAll() {
    return rolRepository.findAll();
  }

  @Override
  public Rol findByRolName(String rolName) {
    return rolRepository.findByRole(rolName)
        .orElseThrow(() -> new EntityNotFoundException("Rol " + rolName + " not found"));
  }
}
