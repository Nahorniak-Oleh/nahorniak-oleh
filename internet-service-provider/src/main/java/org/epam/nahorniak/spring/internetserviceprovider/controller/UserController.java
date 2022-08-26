package org.epam.nahorniak.spring.internetserviceprovider.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.RequestDto;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.UserDto;
import org.epam.nahorniak.spring.internetserviceprovider.service.RequestService;
import org.epam.nahorniak.spring.internetserviceprovider.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    private final RequestService requestService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<UserDto> getAllUsers() {
        log.info("get all users");
        return userService.listUsers();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{email}")
    public UserDto getUser(@PathVariable String email) {
        log.info("getUser by email {}", email);
        return userService.getUser(email);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDto createUser(@RequestBody UserDto userDto) {
        log.info("createUser with email {}", userDto.getEmail());
        return userService.createUser(userDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{email}")
    public UserDto updateUser(@PathVariable String email, @RequestBody UserDto userDto) {
        log.info("updateUser with email {}", email);
        return userService.updateUser(email, userDto);
    }

    @DeleteMapping(value = "/{email}")
    public ResponseEntity<Void> deleteUser(@PathVariable String email) {
        log.info("deleteUser with email {}", email);
        userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{email}/requests")
    public List<RequestDto> getAllRequestsByUser(@PathVariable String email) {
        log.info("getAllRequests By User email {}", email);
        return requestService.getAllByUserEmail(email);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{email}/requests/current")
    public RequestDto getCurrentRequestByUser(@PathVariable String email) {
        log.info("getCurrentRequest By User email {}", email);
        return requestService.getActiveOrSuspendedRequestByUserEmail(email);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{email}/requests/current/close")
    public RequestDto closeCurrentRequestByUser(@PathVariable String email) {
        log.info("closeCurrentRequest By User email {}", email);
        return requestService.closeRequest(email);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{email}/requests/current/activate")
    public RequestDto activateCurrentRequestByUser(@PathVariable String email) {
        log.info("activateCurrentRequest By User email {}", email);
        return requestService.activateRequest(email);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{email}/requests/current/suspend")
    public RequestDto suspendCurrentRequestByUser(@PathVariable String email) {
        log.info("suspendCurrentRequest By User email {}", email);
        return requestService.suspendRequest(email);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{email}/requests/addRequest/{tariffId}")
    public RequestDto addRequest(@PathVariable String email, @PathVariable int tariffId) {
        log.info("addRequest By User email {} and tariffId {}", email, tariffId);
        return requestService.createRequest(email, tariffId);
    }
}
