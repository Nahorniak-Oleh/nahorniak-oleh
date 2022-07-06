package org.epam.nahorniak.spring.homework_3.repository;

import org.epam.nahorniak.spring.homework_3.model.User;

import java.util.List;

public interface UserRepository {

    List<User> listUsers();

    User getUser(String email);

    User createUser(User user);

    User updateUser(String email, User user);

    void deleteUser(String email);
}
