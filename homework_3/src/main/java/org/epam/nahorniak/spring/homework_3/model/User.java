package org.epam.nahorniak.spring.homework_3.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    public String id;
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
