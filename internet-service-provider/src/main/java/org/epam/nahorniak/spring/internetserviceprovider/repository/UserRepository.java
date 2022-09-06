package org.epam.nahorniak.spring.internetserviceprovider.repository;

import org.epam.nahorniak.spring.internetserviceprovider.model.Role;
import org.epam.nahorniak.spring.internetserviceprovider.model.User;

import java.util.List;

public interface UserRepository {

    List<User> listUsers();

    User getUser(String email);

    User createUser(User user);

    User updateUser(String email, User user);

    void deleteUser(String email);

    long countByRole(Role role);
}
