package com.example.demo.dto.response;

import java.io.Serializable;
import lombok.Value;

/**
 * DTO for {@link com.example.demo.entity.Rol}
 */
@Value
public class RolResponse implements Serializable {

  Integer id;
  String role;
  String description;
}