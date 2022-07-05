package org.epam.nahorniak.spring.homework_3.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceDto {

    public int id;
    public String code;
    public String title;

}
