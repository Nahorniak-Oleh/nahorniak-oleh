package org.epam.nahorniak.spring.internetserviceprovider.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TariffServices {

    private int tariffId;
    private int serviceId;

}
