package org.epam.nahorniak.spring.internetserviceprovider.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.epam.nahorniak.spring.internetserviceprovider.model.Role;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private String firstName;

    private String lastName;

    private String email;

    private String phone;

    private String password;

    private Role role;

    private boolean blocked;

    private String street;

    private String country;

    private String city;

    private double balance;
}
