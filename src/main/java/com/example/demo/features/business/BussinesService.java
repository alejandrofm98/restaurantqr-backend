package com.example.demo.features.business;

public interface BussinesService {

  Business updateBusiness(String id, Business business);

  void deleteBusiness(String id);

  Business findBusinessById(String id);

  boolean existsBusinessById(String id);

  Business saveBusiness(Business business);


}
