package org.epam.nahorniak.spring.internetserviceprovider.service.update.impl;

import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.UserDto;
import org.epam.nahorniak.spring.internetserviceprovider.model.User;
import org.epam.nahorniak.spring.internetserviceprovider.model.enums.UserStatus;
import org.epam.nahorniak.spring.internetserviceprovider.service.update.UpdateService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserUpdateServiceImpl implements UpdateService<User, UserDto> {
    @Override
    public User updateObject(User updatable, UserDto dto) {

        final String firstName = dto.getFirstName();
        if(Objects.nonNull(firstName)){
            updatable.setFirstName(firstName);
        }

        final String lastName = dto.getLastName();
        if(Objects.nonNull(lastName)){
            updatable.setLastName(lastName);
        }

        final UserStatus status = dto.getStatus();
        if(Objects.nonNull(status)){
            updatable.setStatus(status);
        }

        final String country = dto.getCountry();
        if(Objects.nonNull(country)){
            updatable.setCountry(country);
        }

        final String city = dto.getCity();
        if(Objects.nonNull(city)){
            updatable.setCity(city);
        }

        final String street = dto.getStreet();
        if(Objects.nonNull(street)){
            updatable.setStreet(street);
        }

        return updatable;
    }
}
