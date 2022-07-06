package org.epam.nahorniak.spring.homework_3.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TariffServices {

    private int tariffId;
    private int serviceId;

}
