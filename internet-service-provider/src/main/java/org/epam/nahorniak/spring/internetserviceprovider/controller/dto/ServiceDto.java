package org.epam.nahorniak.spring.internetserviceprovider.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceDto {

    private int id;

    private String code;

    private String title;

}
