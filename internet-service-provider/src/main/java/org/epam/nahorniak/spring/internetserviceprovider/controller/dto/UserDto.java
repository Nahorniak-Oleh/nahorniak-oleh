package org.epam.nahorniak.spring.internetserviceprovider.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.epam.nahorniak.spring.internetserviceprovider.controller.validation.Phone;
import org.epam.nahorniak.spring.internetserviceprovider.model.enums.Role;
import org.epam.nahorniak.spring.internetserviceprovider.controller.validation.group.OnCreate;
import org.epam.nahorniak.spring.internetserviceprovider.controller.validation.group.OnUpdate;
import org.epam.nahorniak.spring.internetserviceprovider.model.enums.UserStatus;

import javax.validation.constraints.*;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @JsonProperty(access = READ_ONLY)
    private Long id;

    @NotBlank(message = "'first name' shouldn't be empty")
    private String firstName;

    @NotBlank(message = "'last name' shouldn't be empty")
    private String lastName;

    @Email
    @Null(message = "'email' should be absent in request",groups = OnUpdate.class)
    @NotBlank(message = "'email' shouldn't be empty",groups = OnCreate.class)
    private String email;

    @Null(message = "'phone' should be absent in request",groups = OnUpdate.class)
    @Phone(groups = OnCreate.class)
    private String phone;

    @Null(message = "'password' should be absent in request",groups = OnUpdate.class)
    @NotBlank(message = "'password' shouldn't be empty",groups = OnCreate.class)
    private String password;

    @Null(message = "'role' should be absent in request",groups = OnUpdate.class)
    @NotNull(message = "'role' shouldn't be empty",groups = OnCreate.class)
    private Role role;

    @NotNull(message = "'status' shouldn't be empty",groups = OnCreate.class)
    private UserStatus status;

    @NotBlank(message = "'street' shouldn't be empty")
    private String street;

    @NotBlank(message = "'country' shouldn't be empty")
    private String country;

    @NotBlank(message = "'city' shouldn't be empty")
    private String city;

    @PositiveOrZero
    private double balance;
}
