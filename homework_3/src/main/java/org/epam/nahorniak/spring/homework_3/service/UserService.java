package org.epam.nahorniak.spring.homework_3.service;

import org.epam.nahorniak.spring.homework_3.controller.dto.UserDto;
import java.util.List;

public interface UserService {

    List<UserDto> listUsers();

    UserDto getUser(String email);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(String email, UserDto userDto);

    void deleteUser(String email);
}
