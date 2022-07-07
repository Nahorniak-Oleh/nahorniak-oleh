package org.epam.nahorniak.spring.internetserviceprovider.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.epam.nahorniak.spring.internetserviceprovider.model.Role;
import org.epam.nahorniak.spring.internetserviceprovider.model.User;
import org.epam.nahorniak.spring.internetserviceprovider.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    private final List<User> users = new ArrayList<>();

    {
        User user = User.builder()
                .id("1")
                .email("nagornyak68@gmail.com")
                .firstName("Oleh")
                .lastName("Nahorniak")
                .password("111Test")
                .role(Role.CUSTOMER)
                .phone("+380666666666")
                .country("Ukraine")
                .city("Lviv")
                .street("Shevchenka St 111a").build();

        users.add(user);
    }

    @Override
    public List<User> listUsers() {
        log.info("UserRepository --> get all users");
        return new ArrayList<>(users);
    }

    @Override
    public User getUser(String email) {
        log.info("UserRepository --> getUser by email {}", email);
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User is not found!"));
    }

    @Override
    public User createUser(User user) {
        user.setId(String.valueOf(users.size() + 1));
        log.info("UserRepository --> createUser {}", user);
        users.add(user);
        return user;
    }

    @Override
    public User updateUser(String email, User user) {
        log.info("UserRepository --> updateUser with email {}", email);
        boolean isDeleted = users.removeIf(u -> u.getEmail().equals(email));
        if (isDeleted) {
            users.add(user);
        } else {
            throw new RuntimeException("User is not found!");
        }
        return user;
    }

    @Override
    public void deleteUser(String email) {
        log.info("UserRepository --> deleteUser with email {}", email);
        users.removeIf(user -> user.getEmail().equals(email));
    }

    @Override
    public long countByRole(Role role) {
        return users.stream().filter(user -> user.getRole().equals(role)).count();
    }
}
