package org.epam.nahorniak.spring.internetserviceprovider.service;

import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.UserDto;
import org.epam.nahorniak.spring.internetserviceprovider.exception.SuchUserAlreadyExistException;
import org.epam.nahorniak.spring.internetserviceprovider.exception.UserNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.model.User;
import org.epam.nahorniak.spring.internetserviceprovider.repository.UserRepository;
import org.epam.nahorniak.spring.internetserviceprovider.service.impl.UserServiceImpl;
import org.epam.nahorniak.spring.internetserviceprovider.service.update.impl.UserUpdateServiceImpl;
import org.epam.nahorniak.spring.internetserviceprovider.util.TestUserDataUtil;
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
import static org.epam.nahorniak.spring.internetserviceprovider.util.TestUserDataUtil.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Spy
    private UserUpdateServiceImpl updateService;
    @Mock
    private UserRepository userRepository;

    private static final Integer PAGE = 0;
    private static final Integer SIZE = 1;

    @Test
    void listUsersTest(){
        //given
        User expectedUser = TestUserDataUtil.createUser();
        PageRequest pageRequest = PageRequest.of(PAGE, SIZE, Sort.by("lastName").ascending());
        List<User> userList = Collections.singletonList(expectedUser);
        Page<User> usersPage = new PageImpl<>(userList);
        when(userRepository.findAll(pageRequest)).thenReturn(usersPage);

        //when
        List<UserDto> users = userService.listUsers(PAGE,SIZE);

        //then
        assertThat(users, hasSize(1));
    }

    @Test
    void createUserTest(){
        //given
        UserDto createBody = TestUserDataUtil.createUserDto();
        User expectedUser = TestUserDataUtil.createUser();
        when(userRepository.save(any())).thenReturn(expectedUser);

        //when
        createBody = userService.createUser(createBody);

        //then
        assertThat(createBody,allOf(
                hasProperty("firstName",equalTo(expectedUser.getFirstName())),
                hasProperty("lastName",equalTo(expectedUser.getLastName()))
        ));
    }

    @Test
    void getUserByEmailTest(){
        //given
        User expectedUser = TestUserDataUtil.createUser();
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
        User expectedUser = TestUserDataUtil.createUser();
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
        User expectedUser = TestUserDataUtil.createUser();
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
        User expectedUser = TestUserDataUtil.createUser();
        UserDto updateBody = TestUserDataUtil.createUpdatedUserDto();

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
