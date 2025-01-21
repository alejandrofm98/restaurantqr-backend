package com.example.demo.features.business;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BussinesServiceImpl implements BussinesService {

  private final BusinessRepository businessRepository;

  public Business updateBusiness(String id, Business business) {
    if (!businessRepository.existsById(id)) {
      throw new EntityNotFoundException("Business not found with id " + id);
    }

    return businessRepository.save(business);
  }

  public void deleteBusiness(String id) {
    if (!businessRepository.existsById(id)) {
      throw new EntityNotFoundException("Business not found with id " + id);
    }
    businessRepository.deleteById(id);

  }

  public Business findBusinessById(String id) {
    return businessRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Bussines with id " + id + " not found"));
  }

  public boolean existsBusinessById(String id) {
    return businessRepository.existsById(id);
  }

  @Override
  public Business saveBusiness(Business business) {
    return businessRepository.save(business);
  }

}
