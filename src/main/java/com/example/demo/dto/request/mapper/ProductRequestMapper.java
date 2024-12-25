package com.example.demo.dto.request.mapper;

import com.example.demo.dto.request.ProductRequest;
import com.example.demo.entity.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = ComponentModel.SPRING)
public interface ProductRequestMapper {

  Product toEntity(ProductRequest productRequest);

  ProductRequest toDto(Product product);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Product partialUpdate(
      ProductRequest productRequest, @MappingTarget Product product);
}