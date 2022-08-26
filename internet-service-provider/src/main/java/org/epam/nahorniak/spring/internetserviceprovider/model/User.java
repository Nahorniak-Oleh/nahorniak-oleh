package org.epam.nahorniak.spring.internetserviceprovider.model;

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
