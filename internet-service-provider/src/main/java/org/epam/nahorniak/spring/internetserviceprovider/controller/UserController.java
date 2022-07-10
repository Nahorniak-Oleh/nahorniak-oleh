package org.epam.nahorniak.spring.internetserviceprovider.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.epam.nahorniak.spring.internetserviceprovider.api.UserApi;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.RequestDto;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.UserDto;
import org.epam.nahorniak.spring.internetserviceprovider.service.RequestService;
import org.epam.nahorniak.spring.internetserviceprovider.service.UserService;
import org.epam.nahorniak.spring.internetserviceprovider.controller.validation.group.OnCreate;
import org.epam.nahorniak.spring.internetserviceprovider.controller.validation.group.OnUpdate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserApi {

    private final UserService userService;
    private final RequestService requestService;

    @Override
    public List<UserDto> getAllUsers() {
        log.info("get all users");
        return userService.listUsers();
    }

    @Override
    public UserDto getUser(String email) {
        log.info("getUser by email {}", email);
        return userService.getUser(email);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("createUser with email {}", userDto.getEmail());
        return userService.createUser(userDto);
    }

    @Override
    public UserDto updateUser(String email, UserDto userDto) {
        log.info("updateUser with email {}", email);
        return userService.updateUser(email, userDto);
    }

    @Override
    public ResponseEntity<Void> deleteUser(String email) {
        log.info("deleteUser with email {}", email);
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

    @Override
    public List<RequestDto> getAllRequestsByUser(String email) {
        log.info("getAllRequests By User email {}", email);
        return requestService.getAllByUserEmail(email);
    }

    @Override
    public RequestDto getCurrentRequestByUser(String email) {
        log.info("getCurrentRequest By User email {}", email);
        return requestService.getActiveOrSuspendedRequestByUserEmail(email);
    }

    @Override
    public RequestDto closeCurrentRequestByUser(String email) {
        log.info("closeCurrentRequest By User email {}", email);
        return requestService.closeRequest(email);
    }

    @Override
    public RequestDto activateCurrentRequestByUser(String email) {
        log.info("activateCurrentRequest By User email {}", email);
        return requestService.activateRequest(email);
    }

    @Override
    public RequestDto suspendCurrentRequestByUser(String email) {
        log.info("suspendCurrentRequest By User email {}", email);
        return requestService.suspendRequest(email);
    }

    @Override
    public RequestDto addRequest(String email,Long tariffId) {
        log.info("addRequest By User email {} and tariffId {}", email, tariffId);
        return requestService.createRequest(email, tariffId);
    }
}
