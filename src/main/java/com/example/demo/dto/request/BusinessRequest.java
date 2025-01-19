package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.example.demo.entity.Business}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessRequest implements Serializable {

  @NotNull
  @NotEmpty
  @NotBlank
  String name;
  @NotNull
  @NotEmpty
  @NotBlank
  String address;
  @NotNull
  @NotEmpty
  @NotBlank
  String country;
  @NotNull
  @NotEmpty
  @NotBlank
  String documentNumber;
  @NotNull
  @NotEmpty
  @NotBlank
  String documentType;
  boolean isActive;
  String lenguajeIso2;
  String lenguajeIso3;
  String postalCode;
  @NotNull
  @NotEmpty
  @NotBlank
  String province;
  @NotNull
  @NotEmpty
  @NotBlank
  String state;
  @NotNull
  @NotEmpty
  @NotBlank
  String town;
}