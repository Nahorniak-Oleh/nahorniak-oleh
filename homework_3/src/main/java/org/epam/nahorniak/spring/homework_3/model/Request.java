package org.epam.nahorniak.spring.homework_3.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Request {

    private int id;
    private String userId;
    private int tariffId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Status status;

}
