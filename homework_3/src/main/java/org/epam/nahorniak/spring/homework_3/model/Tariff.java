package org.epam.nahorniak.spring.homework_3.model;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class Tariff {

    public int id;
    public String code;
    public String title;
    public double price;
    public List<Service> services;

}
