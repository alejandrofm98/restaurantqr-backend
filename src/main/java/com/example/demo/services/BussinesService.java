package com.example.demo.services;

import com.example.demo.entity.Business;

public interface BussinesService {

  Business updateBusiness(String id, Business business);

  void deleteBusiness(String id);

  Business findBusinessById(String id);

  boolean existsBusinessById(String id);

  Business saveBusiness(Business business);


}
