package org.epam.nahorniak.spring.internetserviceprovider.testUtils;

import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.ServiceDto;
import org.epam.nahorniak.spring.internetserviceprovider.model.ServiceModel;

import java.util.HashSet;

public class TestServiceDataUtil {

    public static final Long MOCK_ID = 1L;
    public static final String MOCK_CODE = "#INTERNET";
    public static final String MOCK_TITLE = "Internet";

    public static final String MOCK_UPDATE_CODE = "#CELL";
    public static final String MOCK_UPDATE_TITLE = "Cell";

    public final static String SERVICE_NOT_FOUND = "Service is not found!";

    public static ServiceModel createService() {
        return ServiceModel.builder()
                .id(MOCK_ID)
                .code(MOCK_CODE)
                .title(MOCK_TITLE)
                .tariffs(new HashSet<>())
                .build();
    }

    public static ServiceDto createServiceDto() {
        return ServiceDto.builder()
                .id(MOCK_ID)
                .code(MOCK_CODE)
                .title(MOCK_TITLE)
                .build();
    }

    public static ServiceDto createUpdateServiceDto() {
        return ServiceDto.builder()
                .id(MOCK_ID)
                .code(MOCK_UPDATE_CODE)
                .title(MOCK_UPDATE_TITLE)
                .build();
    }
}
