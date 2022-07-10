package org.epam.nahorniak.spring.internetserviceprovider.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.UserDto;
import org.epam.nahorniak.spring.internetserviceprovider.exception.SuchUserAlreadyExistException;
import org.epam.nahorniak.spring.internetserviceprovider.exception.UserNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.mapper.UserMapper;
import org.epam.nahorniak.spring.internetserviceprovider.model.User;
import org.epam.nahorniak.spring.internetserviceprovider.repository.UserRepository;
import org.epam.nahorniak.spring.internetserviceprovider.service.update.UpdateService;
import org.epam.nahorniak.spring.internetserviceprovider.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UpdateService<User,UserDto> updateService;

    @Override
    public List<UserDto> listUsers() {
        log.info("UserService --> get all users");
        List<User> users = userRepository.findAll();
        return UserMapper.INSTANCE.mapListOfUserToListOfDto(users);
    }

    @Override
    public UserDto getUser(String email) {
        log.info("UserService --> getUser by email {}", email);
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        return UserMapper.INSTANCE.mapUserToUserDto(user);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("UserService --> createUser {}", userDto);
        if(userRepository.existsByEmail(userDto.getEmail())) {
            throw new SuchUserAlreadyExistException("User with such email already exists");
        }
        if(userRepository.existsByPhone(userDto.getPhone())) {
            throw new SuchUserAlreadyExistException("User with such phone already exists");
        }
        User user = UserMapper.INSTANCE.mapUserDtoToUser(userDto);
        user = userRepository.save(user);
        return UserMapper.INSTANCE.mapUserToUserDto(user);
    }

    @Override
    public UserDto updateUser(String email, UserDto userDto) {
        log.info("UserService --> updateUser with email {}", email);
        User persistedUser = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        persistedUser = updateService.updateObject(persistedUser,userDto);
        User storedUser = persistedUser = userRepository.save(persistedUser);
        log.info("UserService --> User with email {} successfully updated", storedUser.getEmail());

        return UserMapper.INSTANCE.mapUserToUserDto(persistedUser);
    }

    @Override
    public void deleteUser(String email) {
        log.info("UserService --> deleteUser with email {}", email);
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }
}
