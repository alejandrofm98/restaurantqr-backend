package com.example.demo.dto.response;

import java.io.Serializable;
import lombok.Value;


@Value
public class UserResponse implements Serializable {
    Integer id;
    String email;
    String lastname;
    String name;
    String username;
    Boolean status;
    Integer rol;
    String businessUuid;
}
