package com.example.demo.dto.request.mapper;

import com.example.demo.dto.request.BusinessRequest;
import com.example.demo.entity.Business;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface BusinessRequestMapper {

  Business toEntity(BusinessRequest businessRequest);

  BusinessRequest toDto(Business business);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Business partialUpdate(
      BusinessRequest businessRequest, @MappingTarget Business business);
}