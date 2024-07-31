package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    BigInteger id;
    Integer category;
    String description;
    String image;
    String name;
    Float price;
    Integer status;
    String businessUuid;
    Float offer;
    Timestamp publicationDate;
    Float subCategory;
    Boolean trending;
}
