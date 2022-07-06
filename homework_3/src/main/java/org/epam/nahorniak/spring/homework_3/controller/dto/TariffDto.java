package org.epam.nahorniak.spring.homework_3.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TariffDto {

    @JsonProperty(access = READ_ONLY)
    private int id;

    private String code;

    private String title;

    private double price;

    @JsonProperty(access = READ_ONLY)
    private List<ServiceDto> services;
}
