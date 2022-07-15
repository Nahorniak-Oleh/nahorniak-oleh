package org.epam.nahorniak.spring.internetserviceprovider.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.ServiceDto;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.TariffDto;
import org.epam.nahorniak.spring.internetserviceprovider.exception.ServiceNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.exception.TariffNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.model.enums.ErrorType;
import org.epam.nahorniak.spring.internetserviceprovider.service.TariffService;
import org.epam.nahorniak.spring.internetserviceprovider.util.TestServiceDataUtil;
import org.epam.nahorniak.spring.internetserviceprovider.util.TestTariffDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.epam.nahorniak.spring.internetserviceprovider.util.TestRequestDataUtil.REQUEST_NOT_FOUND;
import static org.epam.nahorniak.spring.internetserviceprovider.util.TestServiceDataUtil.SERVICE_NOT_FOUND;
import static org.epam.nahorniak.spring.internetserviceprovider.util.TestTariffDataUtil.*;
import static org.epam.nahorniak.spring.internetserviceprovider.util.TestUserDataUtil.MOCK_EMAIL;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(value = TariffController.class)
@AutoConfigureMockMvc
public class TariffControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TariffService tariffService;

    private static final Integer PAGE = 0;
    private static final Integer SIZE = 1;

    @Test
    void getAllTariffsTest() throws Exception {

        TariffDto expectedTariff = TestTariffDataUtil.createTariffDto();
        when(tariffService.listTariffs(PAGE,SIZE)).thenReturn(Collections.singletonList(expectedTariff));

        mockMvc.perform(
                get("/api/v1/tariffs")
                .param("page", String.valueOf(PAGE))
                .param("size", String.valueOf(SIZE)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(MOCK_ID))
                .andExpect(jsonPath("$[0].code").value(MOCK_CODE))
                .andExpect(jsonPath("$[0].title").value(MOCK_TITLE))
                .andExpect(jsonPath("$[0].price").value(MOCK_PRICE));
    }

    @Test
    void getTariffByIdTest() throws Exception {

        TariffDto expectedTariff = TestTariffDataUtil.createTariffDto();
        when(tariffService.getTariff(MOCK_ID)).thenReturn(expectedTariff);

        mockMvc.perform(
                get("/api/v1/tariffs/" + MOCK_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(MOCK_ID))
                .andExpect(jsonPath("$.code").value(MOCK_CODE))
                .andExpect(jsonPath("$.title").value(MOCK_TITLE))
                .andExpect(jsonPath("$.price").value(MOCK_PRICE));
    }

    @Test
    void createTariffWithValidBodyTest() throws Exception {

        TariffDto createBody = TestTariffDataUtil.createTariffDto();
        createBody.setId(null);
        TariffDto expectedTariff = TestTariffDataUtil.createTariffDto();
        when(tariffService.createTariff(createBody)).thenReturn(expectedTariff);

        mockMvc.perform(
                        post("/api/v1/tariffs/")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(createBody)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(MOCK_ID))
                .andExpect(jsonPath("$.code").value(MOCK_CODE))
                .andExpect(jsonPath("$.title").value(MOCK_TITLE))
                .andExpect(jsonPath("$.price").value(MOCK_PRICE));
    }

    @Test
    void createTariffWithInvalidBodyTest() throws Exception {
        TariffDto createBody = TestTariffDataUtil.createTariffDto();
        createBody.setId(null);
        createBody.setCode("code");
        createBody.setTitle("11111");
        when(tariffService.createTariff(createBody)).thenReturn(createBody);

        mockMvc.perform(
                        post("/api/v1/tariffs/")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(createBody)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[1].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));
    }

    @Test
    void updateTariffWithValidBodyTest() throws Exception {

        TariffDto updateBody = TestTariffDataUtil.createUpdatedTariffDto();
        updateBody.setId(null);
        TariffDto expectedTariff = TestTariffDataUtil.createUpdatedTariffDto();
        when(tariffService.updateTariff(MOCK_ID,updateBody)).thenReturn(expectedTariff);

        mockMvc.perform(
                patch("/api/v1/tariffs/" + MOCK_ID)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updateBody)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(MOCK_ID))
                .andExpect(jsonPath("$.code").value(UPDATE_MOCK_CODE))
                .andExpect(jsonPath("$.title").value(UPDATE_MOCK_TITLE))
                .andExpect(jsonPath("$.price").value(UPDATE_MOCK_PRICE));
    }

    @Test
    void updateTariffWithInvalidBodyTest() throws Exception {

        TariffDto updateBody = TestTariffDataUtil.createUpdatedTariffDto();
        updateBody.setId(null);
        updateBody.setCode("code");
        updateBody.setTitle("rr111");
        when(tariffService.updateTariff(MOCK_ID,updateBody)).thenReturn(updateBody);

        mockMvc.perform(
                        patch("/api/v1/tariffs/" + MOCK_ID)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(updateBody)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[1].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));
    }

    @Test
    void deleteTariffTest() throws Exception {

        doNothing().when(tariffService).deleteTariff(MOCK_ID);

        mockMvc
                .perform(delete("/api/v1/tariffs/" + MOCK_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void addServiceToTariffTest() throws Exception {

        TariffDto expectedTariff = TestTariffDataUtil.createTariffDto();
        ServiceDto expectedService = TestServiceDataUtil.createServiceDto();
        expectedTariff.setServices(Collections.singletonList(expectedService));
        when(tariffService.addService(MOCK_ID,MOCK_ID)).thenReturn(expectedTariff);

        mockMvc.perform(
                patch("/api/v1/tariffs/" + MOCK_ID + "/addService/" + MOCK_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.services[0].id").value(MOCK_ID))
                .andExpect(jsonPath("$.services[0].code").value(TestServiceDataUtil.MOCK_CODE))
                .andExpect(jsonPath("$.services[0].title").value(TestServiceDataUtil.MOCK_TITLE));
    }

    @Test
    void deleteServiceFromTariffTest() throws Exception {

        TariffDto expectedTariff = TestTariffDataUtil.createTariffDto();
        when(tariffService.removeService(MOCK_ID,MOCK_ID)).thenReturn(expectedTariff);

        mockMvc.perform(
                        patch("/api/v1/tariffs/" + MOCK_ID + "/deleteService/" + MOCK_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.services").doesNotExist());
    }

    //Exceptions

    @Test
    void getTariffById_TariffNotFoundTest() throws Exception {

        when(tariffService.getTariff(MOCK_ID)).thenThrow(new TariffNotFoundException());

        mockMvc.perform(
                        get("/api/v1/tariffs/" + MOCK_ID))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(TARIFF_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }

    @Test
    void updateTariff_TariffNotFoundTest() throws Exception {

        TariffDto updateBody = TestTariffDataUtil.createUpdatedTariffDto();
        updateBody.setId(null);
        when(tariffService.updateTariff(MOCK_ID,updateBody)).thenThrow(new TariffNotFoundException());

        mockMvc.perform(
                        patch("/api/v1/tariffs/" + MOCK_ID)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(updateBody)))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.message").value(TARIFF_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }

    @Test
    void deleteTariff_TariffNotFoundTest() throws Exception {

        doThrow(new TariffNotFoundException()).when(tariffService).deleteTariff(MOCK_ID);

        mockMvc.perform(
                        delete("/api/v1/tariffs/" + MOCK_ID))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(TARIFF_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }

    @Test
    void addServiceToTariff_TariffNotFoundTest() throws Exception {

        when(tariffService.addService(MOCK_ID,MOCK_ID)).thenThrow(new TariffNotFoundException());

        mockMvc.perform(
                        patch("/api/v1/tariffs/" + MOCK_ID + "/addService/" + MOCK_ID))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(TARIFF_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }

    @Test
    void addServiceToTariff_ServiceNotFoundTest() throws Exception {

        when(tariffService.addService(MOCK_ID,MOCK_ID)).thenThrow(new ServiceNotFoundException());

        mockMvc.perform(
                        patch("/api/v1/tariffs/" + MOCK_ID + "/addService/" + MOCK_ID))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(SERVICE_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }

    @Test
    void deleteServiceFromTariff_TariffNotFoundTest() throws Exception {

        when(tariffService.removeService(MOCK_ID,MOCK_ID)).thenThrow(new TariffNotFoundException());

        mockMvc.perform(
                        patch("/api/v1/tariffs/" + MOCK_ID + "/deleteService/" + MOCK_ID))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(TARIFF_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }

    @Test
    void deleteServiceFromTariff_ServiceNotFoundTest() throws Exception {

        when(tariffService.removeService(MOCK_ID,MOCK_ID)).thenThrow(new ServiceNotFoundException());

        mockMvc.perform(
                        patch("/api/v1/tariffs/" + MOCK_ID + "/deleteService/" + MOCK_ID))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(SERVICE_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }
}
