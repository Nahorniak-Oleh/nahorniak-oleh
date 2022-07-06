package org.epam.nahorniak.spring.homework_3.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Service {

    private int id;
    private String code;
    private String title;

}
