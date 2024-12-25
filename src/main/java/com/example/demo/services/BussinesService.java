package com.example.demo.services;

import com.example.demo.entity.Business;

public interface BussinesService {

  Business updateBusiness(String id, Business business);

  void deleteBusiness(String id);

  Business getBusinessById(String id);

}
