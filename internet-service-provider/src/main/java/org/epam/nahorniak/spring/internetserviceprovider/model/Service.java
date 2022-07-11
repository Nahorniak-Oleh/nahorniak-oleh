package org.epam.nahorniak.spring.internetserviceprovider.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Service {

    private int id;
    private String code;
    private String title;

}
