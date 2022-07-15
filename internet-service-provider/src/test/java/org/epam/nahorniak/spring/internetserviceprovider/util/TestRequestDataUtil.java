package org.epam.nahorniak.spring.internetserviceprovider.util;

import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.RequestDto;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.TariffDto;
import org.epam.nahorniak.spring.internetserviceprovider.model.Request;
import org.epam.nahorniak.spring.internetserviceprovider.model.Tariff;
import org.epam.nahorniak.spring.internetserviceprovider.model.User;
import org.epam.nahorniak.spring.internetserviceprovider.model.enums.RequestStatus;

import java.time.LocalDate;

public class TestRequestDataUtil {

    public static final Long MOCK_ID = 1L;
    public static final LocalDate MOCK_START_DATE = LocalDate.now();
    public static final LocalDate MOCK_END_DATE = MOCK_START_DATE.plusMonths(1);
    public static final RequestStatus MOCK_STATUS = RequestStatus.ACTIVE;
    public static final String MOCK_EMAIL = "EMAIL@gmail.com";

    private static final User USER = TestUserDataUtil.createUser();
    private static final Tariff TARIFF = TestTariffDataUtil.createTariff();
    private static final TariffDto TARIFF_DTO = TestTariffDataUtil.createTariffDto();

    public final static String REQUEST_NOT_FOUND = "Request is not found!";

    public static Request createRequest() {
        return Request.builder()
                .id(MOCK_ID)
                .user(USER)
                .tariff(TARIFF)
                .startDate(MOCK_START_DATE)
                .endDate(MOCK_END_DATE)
                .status(MOCK_STATUS).build();
    }

    public static RequestDto createRequestDto() {
        return RequestDto.builder()
                .id(MOCK_ID)
                .tariffDto(TARIFF_DTO)
                .startDate(MOCK_START_DATE)
                .endDate(MOCK_END_DATE)
                .status(MOCK_STATUS).build();
    }
}
