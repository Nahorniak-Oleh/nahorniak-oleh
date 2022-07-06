package org.epam.nahorniak.spring.homework_3.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.epam.nahorniak.spring.homework_3.model.Status;

import java.time.LocalDate;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestDto {

    private int id;

    private TariffDto tariffDto;

    private LocalDate startDate;

    private LocalDate endDate;

    private Status status;

}
