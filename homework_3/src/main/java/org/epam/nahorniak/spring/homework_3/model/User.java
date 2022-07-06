package org.epam.nahorniak.spring.homework_3.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private String id;
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
