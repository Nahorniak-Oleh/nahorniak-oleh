package org.epam.nahorniak.spring.homework_3.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Request {

    public int id;
    public String userId;
    public int tariffId;
    public LocalDate startDate;
    public LocalDate endDate;
    public Status status;

}
