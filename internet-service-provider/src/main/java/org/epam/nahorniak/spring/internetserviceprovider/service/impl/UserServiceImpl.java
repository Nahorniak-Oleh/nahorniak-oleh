package org.epam.nahorniak.spring.internetserviceprovider.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.UserDto;
import org.epam.nahorniak.spring.internetserviceprovider.mapper.UserMapper;
import org.epam.nahorniak.spring.internetserviceprovider.model.User;
import org.epam.nahorniak.spring.internetserviceprovider.repository.UserRepository;
import org.epam.nahorniak.spring.internetserviceprovider.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserDto> listUsers() {
        log.info("UserService --> get all users");
        List<User> users = userRepository.listUsers();
        return UserMapper.INSTANCE.mapListOfUserToListOfDto(users);
    }

    @Override
    public UserDto getUser(String email) {
        log.info("UserService --> getUser by email {}", email);
        User user = userRepository.getUser(email);
        return UserMapper.INSTANCE.mapUserToUserDto(user);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("UserService --> createUser {}", userDto);
        User user = UserMapper.INSTANCE.mapUserDtoToUser(userDto);
        user = userRepository.createUser(user);
        return UserMapper.INSTANCE.mapUserToUserDto(user);
    }

    @Override
    public UserDto updateUser(String email, UserDto userDto) {
        log.info("UserService --> updateUser with email {}", email);
        User user = UserMapper.INSTANCE.mapUserDtoToUser(userDto);
        user = userRepository.updateUser(email, user);
        return UserMapper.INSTANCE.mapUserToUserDto(user);
    }

    @Override
    public void deleteUser(String email) {
        log.info("UserService --> deleteUser with email {}", email);
        userRepository.deleteUser(email);
    }
}
