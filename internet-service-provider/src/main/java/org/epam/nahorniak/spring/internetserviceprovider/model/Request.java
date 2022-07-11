package org.epam.nahorniak.spring.internetserviceprovider.model;

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
