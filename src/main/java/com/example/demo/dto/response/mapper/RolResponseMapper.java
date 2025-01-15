package com.example.demo.dto.response.mapper;

import com.example.demo.dto.response.RolResponse;
import com.example.demo.entity.Rol;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface RolResponseMapper {

  Rol toEntity(RolResponse rolResponse);

  RolResponse toDto(Rol rol);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Rol partialUpdate(
      RolResponse rolResponse, @MappingTarget Rol rol);
}