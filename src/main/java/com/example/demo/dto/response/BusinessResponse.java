package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusinessResponse {
    String businessUuid;
    String name;
    String address;
    String country;
    String documentNumber;
    String documentType;
    boolean isActive;
    String lenguajeIso2;
    String lenguajeIso3;
    String postalCode;
    String province;
    String state;
    String town;
    String createdAt;
    String updatedAt;
}
