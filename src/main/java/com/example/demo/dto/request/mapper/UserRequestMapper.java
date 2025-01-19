package com.example.demo.dto.request.mapper;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.BusinessRepository;
import com.example.demo.repository.RolRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = ComponentModel.SPRING)
public abstract class UserRequestMapper {

  protected RolRepository rolRepository;
  protected BusinessRepository businessRepository;

  @Autowired
  protected void setRolRepository(RolRepository repository) {
    this.rolRepository = repository;
  }

  @Autowired
  protected void setBusinessRepository(BusinessRepository repository) {
    this.businessRepository = repository;
  }


  @Mapping(source = "rol", target = "rol.id")
  @Mapping(source = "businessUuid", target = "business.businessUuid")
  public User toEntity(UserRequest userRequest) {
    User user = new User();
    BeanUtils.copyProperties(userRequest, user);
    user.setRol(rolRepository.findById(userRequest.getRol()).orElse(null));
    user.setBusiness(businessRepository.findById(userRequest.getBusinessUuid()).orElse(null));
    return user;
  }

  @Mapping(source = "rol.id", target = "rol")
  @Mapping(source = "business.businessUuid", target = "businessUuid")
  public UserRequest toDto(User user) {
    UserRequest userRequest = new UserRequest();
    BeanUtils.copyProperties(user, userRequest);
    return userRequest;
  }

}