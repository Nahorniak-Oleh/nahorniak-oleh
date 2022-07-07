package org.epam.nahorniak.spring.internetserviceprovider.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.epam.nahorniak.spring.internetserviceprovider.controller.validation.Phone;
import org.epam.nahorniak.spring.internetserviceprovider.model.Role;
import org.epam.nahorniak.spring.internetserviceprovider.controller.validation.group.OnCreate;
import org.epam.nahorniak.spring.internetserviceprovider.controller.validation.group.OnUpdate;

import javax.validation.constraints.*;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @NotBlank(message = "'first name' shouldn't be empty",groups = OnCreate.class)
    private String firstName;

    @NotBlank(message = "'last name' shouldn't be empty",groups = OnCreate.class)
    private String lastName;

    @Email
    @Null(message = "'email' should be absent in request",groups = OnUpdate.class)
    @NotBlank(message = "'email' shouldn't be empty",groups = OnCreate.class)
    private String email;

    @Phone
    private String phone;

    @Null(message = "'phone' should be absent in request",groups = OnUpdate.class)
    @NotBlank(message = "'phone' shouldn't be empty",groups = OnCreate.class)
    private String password;

    @Null(message = "'phone' should be absent in request",groups = OnUpdate.class)
    @NotNull(message = "'phone' shouldn't be empty",groups = OnCreate.class)
    private Role role;

    @JsonProperty(access = READ_ONLY)
    private boolean blocked;

    @NotBlank(message = "'street' shouldn't be empty")
    private String street;

    @NotBlank(message = "'country' shouldn't be empty")
    private String country;

    @NotBlank(message = "'city' shouldn't be empty")
    private String city;

    @PositiveOrZero
    private double balance;
}
