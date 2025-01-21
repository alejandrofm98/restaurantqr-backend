package com.example.demo.features.rol;

import java.io.Serializable;
import lombok.Value;

/**
 * DTO for {@link Rol}
 */
@Value
public class RolResponse implements Serializable {

  Integer id;
  String role;
  String description;
}