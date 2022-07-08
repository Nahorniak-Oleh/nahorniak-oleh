package org.epam.nahorniak.spring.internetserviceprovider.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.RequestDto;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.UserDto;
import org.epam.nahorniak.spring.internetserviceprovider.controller.validation.group.OnCreate;
import org.epam.nahorniak.spring.internetserviceprovider.controller.validation.group.OnUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "User management API")
@RequestMapping("/api/v1/user")
public interface UserApi {

    @ApiOperation("Get all users")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    List<UserDto> getAllUsers();

    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", paramType = "path", required = true, value = "User email")
    })
    @ApiOperation("Get user by email")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{email}")
    UserDto getUser(@PathVariable String email);

    @ApiOperation("Create user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    UserDto createUser(@RequestBody @Validated(OnCreate.class) UserDto userDto);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", paramType = "path", required = true, value = "User email")
    })
    @ApiOperation("Update user")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{email}")
    UserDto updateUser(@PathVariable String email,
                              @RequestBody @Validated(OnUpdate.class) UserDto userDto);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", paramType = "path", required = true, value = "User email")
    })
    @ApiOperation("Delete user")
    @DeleteMapping(value = "/{email}")
    ResponseEntity<Void> deleteUser(@PathVariable String email);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", paramType = "path", required = true, value = "User email")
    })
    @ApiOperation("get all user's requests")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{email}/requests")
    List<RequestDto> getAllRequestsByUser(@PathVariable String email);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", paramType = "path", required = true, value = "User email")
    })
    @ApiOperation("get current user's request")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{email}/requests/current")
    RequestDto getCurrentRequestByUser(@PathVariable String email);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", paramType = "path", required = true, value = "User email")
    })
    @ApiOperation("close current user's request")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{email}/requests/current/close")
    RequestDto closeCurrentRequestByUser(@PathVariable String email);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", paramType = "path", required = true, value = "User email")
    })
    @ApiOperation("activate current user's request")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{email}/requests/current/activate")
    RequestDto activateCurrentRequestByUser(@PathVariable String email);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", paramType = "path", required = true, value = "User email")
    })
    @ApiOperation("suspend current user's request")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{email}/requests/current/suspend")
    RequestDto suspendCurrentRequestByUser(@PathVariable String email);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", paramType = "path", required = true, value = "User email"),
            @ApiImplicitParam(name = "tariffId", paramType = "path", required = true, value = "Tariff id")
    })
    @ApiOperation("add request to user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/{email}/requests/addRequest/{tariffId}")
    RequestDto addRequest(@PathVariable String email, @PathVariable int tariffId);
}
