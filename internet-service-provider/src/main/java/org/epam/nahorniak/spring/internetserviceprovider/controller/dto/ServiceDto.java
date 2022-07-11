package org.epam.nahorniak.spring.internetserviceprovider.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceDto {

    @JsonProperty(access = READ_ONLY)
    private int id;

    @Pattern(regexp = "#[A-Z-]{1,15}",message = "'code' should starts with # and contain only capital letters")
    private String code;

    @Pattern(regexp = "[A-Z]([A-Za-z +-]{3,35})",message = "'title' should contain only letters")
    private String title;

}
