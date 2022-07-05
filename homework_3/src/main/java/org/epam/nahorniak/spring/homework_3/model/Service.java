package org.epam.nahorniak.spring.homework_3.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Service {

    public int id;
    public String code;
    public String title;

}
