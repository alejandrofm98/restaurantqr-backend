package com.example.demo.dto.response.mapper;

import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.User;
import com.example.demo.services.UserService;
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

  @Mapping(source = "rol", target = "rol.id")
  @Mapping(source = "businessUuid", target = "business.businessUuid")
  User toEntity(UserResponse userResponse);

  @Mapping(source = "rol.id", target = "rol")
  @Mapping(source = "business.businessUuid", target = "businessUuid")
  UserResponse toDto(User user);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(source = "rol", target = "rol.id")
  @Mapping(source = "businessUuid", target = "business.businessUuid")
  User partialUpdate(UserResponse userResponse, @MappingTarget User user);
}