package org.epam.nahorniak.spring.internetserviceprovider.util;

import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.TariffDto;
import org.epam.nahorniak.spring.internetserviceprovider.model.Tariff;

import java.util.HashSet;

public class TestTariffDataUtil {

    public static final Long MOCK_ID = 1L;
    public static final String MOCK_CODE = "#STANDARD";
    public static final String MOCK_TITLE = "Standard";
    public static final Double MOCK_PRICE = 5.5;

    public static final String UPDATE_MOCK_CODE = "#FAST";
    public static final String UPDATE_MOCK_TITLE = "Fast";
    public static final Double UPDATE_MOCK_PRICE = 3.5;

    public final static String TARIFF_NOT_FOUND = "Tariff is not found!";

    public static Tariff createTariff(){
        return Tariff.builder()
                .id(MOCK_ID)
                .code(MOCK_CODE)
                .title(MOCK_TITLE)
                .price(MOCK_PRICE)
                .services(new HashSet<>())
                .build();
    }

    public static TariffDto createTariffDto(){
        return TariffDto.builder()
                .id(MOCK_ID)
                .code(MOCK_CODE)
                .title(MOCK_TITLE)
                .price(MOCK_PRICE)
                .build();
    }

    public static TariffDto createUpdatedTariffDto(){
        return TariffDto.builder()
                .id(MOCK_ID)
                .code(UPDATE_MOCK_CODE)
                .title(UPDATE_MOCK_TITLE)
                .price(UPDATE_MOCK_PRICE)
                .build();
    }
}
