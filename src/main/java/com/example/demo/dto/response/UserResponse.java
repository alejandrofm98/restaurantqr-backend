package com.example.demo.dto.response;

import java.io.Serializable;
import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class UserResponse implements Serializable {
    Integer id;
    String email;
    String lastname;
    String name;
    String username;
    Boolean status;
    String role;
    String businessUuid;
}
