package org.epam.nahorniak.spring.homework_3.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.epam.nahorniak.spring.homework_3.model.Role;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    public String firstName;
    public String lastName;
    public String email;
    public String phone;
    public String password;
    public Role role;
    public boolean isBlocked;
    public String street;
    public String country;
    public String city;
    public double balance;
}
