package org.epam.nahorniak.spring.internetserviceprovider.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.UserDto;
import org.epam.nahorniak.spring.internetserviceprovider.model.User;
import org.epam.nahorniak.spring.internetserviceprovider.model.enums.Role;
import org.epam.nahorniak.spring.internetserviceprovider.model.enums.UserStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUserDataUtil {

    public final static Long MOCK_ID = 1L;
    public final static String MOCK_EMAIL = "email@gmail.com";
    public final static String MOCK_FIRST_NAME = "Stephen";
    public final static String MOCK_LAST_NAME = "Curry";
    public final static String MOCK_PASSWORD = "PASSWORD";
    public final static String MOCK_PHONE = "(066) 666-6666";
    public final static UserStatus MOCK_STATUS = UserStatus.BLOCKED;
    public final static Role MOCK_ROLE = Role.ADMIN;
    public final static String MOCK_COUNTRY = "Ukraine";
    public final static String MOCK_CITY = "Lviv";
    public final static String MOCK_STREET = "Shevchenka st. 111a";

    public final static String MOCK_UPDATE_FIRST_NAME = "Bob";
    public final static String MOCK_UPDATE_LAST_NAME = "Tatum";
    public final static String MOCK_UPDATE_COUNTRY = "Poland";
    public final static String MOCK_UPDATE_CITY = "Warsaw";
    public final static String MOCK_UPDATE_STREET = "Arktyczna St. 111";
    public final static UserStatus MOCK_UPDATE_STATUS = UserStatus.ACTIVE;

    public static User createUser() {
        return User.builder()
                .firstName(MOCK_FIRST_NAME)
                .lastName(MOCK_LAST_NAME)
                .email(MOCK_EMAIL)
                .phone(MOCK_PHONE)
                .role(MOCK_ROLE)
                .status(MOCK_STATUS)
                .password(MOCK_PASSWORD)
                .country(MOCK_COUNTRY)
                .city(MOCK_CITY)
                .street(MOCK_STREET)
                .build();
    }

    public static UserDto createUserDto() {
        return UserDto.builder()
                .firstName(MOCK_FIRST_NAME)
                .lastName(MOCK_LAST_NAME)
                .email(MOCK_EMAIL)
                .phone(MOCK_PHONE)
                .role(MOCK_ROLE)
                .status(MOCK_STATUS)
                .password(MOCK_PASSWORD)
                .country(MOCK_COUNTRY)
                .city(MOCK_CITY)
                .street(MOCK_STREET)
                .build();
    }

    public static UserDto createUpdatedUserDto() {
        return UserDto.builder()
                .firstName(MOCK_UPDATE_FIRST_NAME)
                .lastName(MOCK_UPDATE_LAST_NAME)
                .email(MOCK_EMAIL)
                .phone(MOCK_PHONE)
                .role(MOCK_ROLE)
                .status(MOCK_UPDATE_STATUS)
                .password(MOCK_PASSWORD)
                .country(MOCK_UPDATE_COUNTRY)
                .city(MOCK_UPDATE_CITY)
                .street(MOCK_UPDATE_STREET)
                .build();
    }

}
