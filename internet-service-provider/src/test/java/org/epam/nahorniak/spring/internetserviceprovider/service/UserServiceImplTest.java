package org.epam.nahorniak.spring.internetserviceprovider.service;

import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.UserDto;
import org.epam.nahorniak.spring.internetserviceprovider.exception.SuchUserAlreadyExistException;
import org.epam.nahorniak.spring.internetserviceprovider.exception.UserNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.model.User;
import org.epam.nahorniak.spring.internetserviceprovider.model.enums.UserStatus;
import org.epam.nahorniak.spring.internetserviceprovider.repository.UserRepository;
import org.epam.nahorniak.spring.internetserviceprovider.service.impl.UserServiceImpl;
import org.epam.nahorniak.spring.internetserviceprovider.service.update.impl.UserUpdateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Spy
    private UserUpdateServiceImpl updateService;
    @Mock
    private UserRepository userRepository;

    private static User expectedUser;

    private final static String MOCK_EMAIL = "EMAIL@gmail.com";
    private final static String MOCK_FIRST_NAME = "Stephen";
    private final static String MOCK_LAST_NAME = "Curry";
    private final static String MOCK_PASSWORD = "PASSWORD";
    private final static String MOCK_PHONE = "(066) 666-6666";

    private final static String MOCK_UPDATE_FIRST_NAME = "Bob";
    private final static String MOCK_UPDATE_LAST_NAME = "Tatum";
    private final static String MOCK_UPDATE_COUNTRY = "Ukraine";
    private final static String MOCK_UPDATE_CITY = "Lviv";
    private final static String MOCK_UPDATE_STREET = "Shevchenka st. 111a";
    private final static UserStatus MOCK_UPDATE_STATUS = UserStatus.ACTIVE;

    @BeforeEach
    public void initEach(){
        expectedUser = User.builder()
                .email(MOCK_EMAIL).firstName(MOCK_FIRST_NAME)
                .lastName(MOCK_LAST_NAME).password(MOCK_PASSWORD)
                .build();
    }

    @Test
    void listUsersTest(){
        //given
        int page = 0, size = 1;
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("lastName").ascending());
        List<User> userList = Collections.singletonList(expectedUser);
        Page<User> usersPage = new PageImpl<>(userList);
        when(userRepository.findAll(pageRequest)).thenReturn(usersPage);

        //when
        List<UserDto> users = userService.listUsers(page,size);

        //then
        assertThat(users, hasSize(1));
    }

    @Test
    void createUserTest(){
        //given
        UserDto createBody = UserDto.builder()
                .email(MOCK_EMAIL).firstName(MOCK_FIRST_NAME)
                .lastName(MOCK_LAST_NAME).password(MOCK_PASSWORD)
                .build();
        when(userRepository.save(any())).thenReturn(expectedUser);

        //when
        createBody = userService.createUser(createBody);

        assertThat(createBody,allOf(
                hasProperty("firstName",equalTo(expectedUser.getFirstName())),
                hasProperty("lastName",equalTo(expectedUser.getLastName()))
        ));
    }

    @Test
    void getUserByEmailTest(){
        //given
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedUser));

        //when
        UserDto actualUser = userService.getUser(MOCK_EMAIL);

        //then
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertThat(actualUser,allOf(
                hasProperty("firstName",equalTo(expectedUser.getFirstName())),
                hasProperty("lastName",equalTo(expectedUser.getLastName()))
        ));
    }

    @Test
    void deleteUserTest() {
        //given
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedUser));
        doNothing().when(userRepository).delete(any());

        //when
        userService.deleteUser(MOCK_EMAIL);

        //then
        verify(userRepository, times(1)).delete(expectedUser);
    }

    @Test
    void updateUserWithOneFieldChangedTest(){
        //given
        UserDto updateBody = UserDto.builder().email(MOCK_EMAIL).firstName(MOCK_UPDATE_FIRST_NAME).build();
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedUser));
        when(userRepository.save(any())).thenReturn(expectedUser);

        //when
        updateBody = userService.updateUser(MOCK_EMAIL,updateBody);

        //then
        assertThat(updateBody, allOf(
           hasProperty("firstName",equalTo(expectedUser.getFirstName())),
           hasProperty("lastName",equalTo(expectedUser.getLastName())),
           hasProperty("email",equalTo(expectedUser.getEmail()))
        ));
    }

    @Test
    void updateUserWithAllFieldsChangedTest(){
        //given
        UserDto updateBody = UserDto.builder()
                .email(MOCK_EMAIL).firstName(MOCK_UPDATE_FIRST_NAME)
                .lastName(MOCK_UPDATE_LAST_NAME).status(MOCK_UPDATE_STATUS)
                .country(MOCK_UPDATE_COUNTRY).city(MOCK_UPDATE_CITY)
                .street(MOCK_UPDATE_STREET).build();

        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedUser));
        when(userRepository.save(any())).thenReturn(expectedUser);

        //when
        updateBody = userService.updateUser(MOCK_EMAIL,updateBody);

        //then
        assertThat(updateBody, allOf(
                hasProperty("firstName",equalTo(expectedUser.getFirstName())),
                hasProperty("lastName",equalTo(expectedUser.getLastName())),
                hasProperty("email",equalTo(expectedUser.getEmail()))
        ));
    }

    @Test
    void createUserWithSuchEmailExistsTest(){
        UserDto createBody = UserDto.builder().email(MOCK_EMAIL).phone(MOCK_PHONE).build();
        when(userRepository.existsByEmail(MOCK_EMAIL)).thenReturn(true);
        assertThrows(SuchUserAlreadyExistException.class , () -> userService.createUser(createBody));
    }

    @Test
    void createUserWithSuchPhoneExistsTest(){
        UserDto createBody = UserDto.builder().email(MOCK_EMAIL).phone(MOCK_PHONE).build();
        when(userRepository.existsByPhone(MOCK_PHONE)).thenReturn(true);
        assertThrows(SuchUserAlreadyExistException.class , () -> userService.createUser(createBody));
    }

    @Test
    void getUserByEmailNotFoundTest(){
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.getUser(MOCK_EMAIL));
    }

    @Test
    void deleteUserNotFoundTest(){
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(MOCK_EMAIL));
    }

    @Test
    void updateUserNotFoundTest(){
        UserDto updateBody = UserDto.builder().email(MOCK_EMAIL).build();
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(MOCK_EMAIL,updateBody));
    }
}
