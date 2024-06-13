package com.example.demo.services;

import com.example.demo.entity.Business;
import com.example.demo.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BussinesService {

  private final BusinessRepository businessRepository;

  public Business updateBusiness(String id, Business business) {
    if (!businessRepository.existsById(id)) {
      throw new RuntimeException("Business not found with id " + id);
    }

    return businessRepository.save(business);
  }

  public void deleteBusiness(String id) {
    if (!businessRepository.existsById(id)) {
      throw new RuntimeException("Business not found with id " + id);
    }
    businessRepository.deleteById(id);

  }
}
