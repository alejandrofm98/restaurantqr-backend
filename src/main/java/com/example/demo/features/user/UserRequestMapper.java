package com.example.demo.features.user;

import com.example.demo.features.business.BusinessRepository;
import com.example.demo.features.rol.RolRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = ComponentModel.SPRING)
public abstract class UserRequestMapper {

  protected RolRepository rolRepository;
  protected BusinessRepository businessRepository;
  protected PasswordEncoder passwordEncoder;

  @Autowired
  protected void setRolRepository(RolRepository repository) {
    this.rolRepository = repository;
  }

  @Autowired
  protected void setBusinessRepository(BusinessRepository repository) {
    this.businessRepository = repository;
  }

  @Autowired
  protected void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }


  @Mapping(source = "role", target = "rol.role")
  @Mapping(source = "businessUuid", target = "business.businessUuid")
  public User toEntity(UserRequest userRequest) {
    User user = new User();
    BeanUtils.copyProperties(userRequest, user);
    user.setRol(rolRepository.findByRole(userRequest.getRole()).orElse(null));
    user.setBusiness(businessRepository.findById(userRequest.getBusinessUuid()).orElse(null));
    user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
    return user;
  }

  @Mapping(source = "rol.role", target = "role")
  @Mapping(source = "business.businessUuid", target = "businessUuid")
  public UserRequest toDto(User user) {
    UserRequest userRequest = new UserRequest();
    BeanUtils.copyProperties(user, userRequest);
    return userRequest;
  }

}