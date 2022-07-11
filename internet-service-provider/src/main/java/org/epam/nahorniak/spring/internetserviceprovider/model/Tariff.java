package org.epam.nahorniak.spring.internetserviceprovider.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Tariff {

    private int id;
    private String code;
    private String title;
    private double price;
    private List<Service> services;

}
