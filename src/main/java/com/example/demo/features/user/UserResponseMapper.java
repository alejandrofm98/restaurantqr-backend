package com.example.demo.features.user;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING, uses = {
    UserService.class})
public interface UserResponseMapper {

  @Mapping(source = "role", target = "rol.role")
  @Mapping(source = "businessUuid", target = "business.businessUuid")
  User toEntity(UserResponse userResponse);

  @Mapping(source = "rol.role", target = "role")
  @Mapping(source = "business.businessUuid", target = "businessUuid")
  UserResponse toDto(User user);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(source = "role", target = "rol.role")
  @Mapping(source = "businessUuid", target = "business.businessUuid")
  User partialUpdate(UserResponse userResponse, @MappingTarget User user);
}