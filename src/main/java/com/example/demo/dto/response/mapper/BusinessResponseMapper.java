package com.example.demo.dto.response.mapper;

import com.example.demo.dto.response.BusinessResponse;
import com.example.demo.entity.Business;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface BusinessResponseMapper {

  Business toEntity(BusinessResponse businessResponse);

  BusinessResponse toDto(Business business);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Business partialUpdate(
      BusinessResponse businessResponse, @MappingTarget Business business);
}