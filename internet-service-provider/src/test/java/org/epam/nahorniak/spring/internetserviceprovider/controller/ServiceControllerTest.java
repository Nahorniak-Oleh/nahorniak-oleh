package org.epam.nahorniak.spring.internetserviceprovider.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.ServiceDto;
import org.epam.nahorniak.spring.internetserviceprovider.exception.ServiceNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.model.enums.ErrorType;
import org.epam.nahorniak.spring.internetserviceprovider.service.ServicesService;
import org.epam.nahorniak.spring.internetserviceprovider.service.TariffService;
import org.epam.nahorniak.spring.internetserviceprovider.util.TestServiceDataUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.epam.nahorniak.spring.internetserviceprovider.util.TestServiceDataUtil.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(value = ServiceController.class)
@AutoConfigureMockMvc
public class ServiceControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ServicesService servicesService;

    private static final Integer PAGE = 0;
    private static final Integer SIZE = 1;

    @Test
    void getAllServicesTest() throws Exception {

        ServiceDto expectedService = TestServiceDataUtil.createServiceDto();
        when(servicesService.listServices(PAGE,SIZE)).thenReturn(Collections.singletonList(expectedService));

        mockMvc.perform(
                get("/api/v1/services")
                        .param("page", String.valueOf(PAGE))
                        .param("size", String.valueOf(SIZE)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(MOCK_ID))
                .andExpect(jsonPath("$[0].code").value(MOCK_CODE))
                .andExpect(jsonPath("$[0].title").value(MOCK_TITLE));
    }

    @Test
    void getServiceByIdTest() throws Exception {

        ServiceDto expectedService = TestServiceDataUtil.createServiceDto();
        when(servicesService.getService(MOCK_ID)).thenReturn(expectedService);

        mockMvc.perform(
                get("/api/v1/services/"+ MOCK_ID ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(MOCK_ID))
                .andExpect(jsonPath("$.code").value(MOCK_CODE))
                .andExpect(jsonPath("$.title").value(MOCK_TITLE));
    }

    @Test
    void createServiceWithValidBodyTest() throws Exception {

        ServiceDto createBody = TestServiceDataUtil.createServiceDto();
        createBody.setId(null);
        ServiceDto expectedService = TestServiceDataUtil.createServiceDto();
        when(servicesService.createService(createBody)).thenReturn(expectedService);

        mockMvc.perform(
                post("/api/v1/services")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(createBody)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(MOCK_ID))
                .andExpect(jsonPath("$.code").value(MOCK_CODE))
                .andExpect(jsonPath("$.title").value(MOCK_TITLE));
    }

    @Test
    void createServiceWithInvalidBodyTest() throws Exception {

        ServiceDto createBody = TestServiceDataUtil.createServiceDto();
        createBody.setId(null);
        createBody.setCode("code");
        createBody.setTitle("title11");
        when(servicesService.createService(createBody)).thenReturn(createBody);

        mockMvc.perform(
                        post("/api/v1/services")
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(createBody)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[1].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));
    }

    @Test
    void updateServiceWithValidBodyTest() throws Exception {

        ServiceDto updateBody = TestServiceDataUtil.createUpdateServiceDto();
        updateBody.setId(null);
        ServiceDto expectedService = TestServiceDataUtil.createUpdateServiceDto();
        when(servicesService.updateService(MOCK_ID,updateBody)).thenReturn(expectedService);

        mockMvc.perform(
                patch("/api/v1/services/" + MOCK_ID)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(updateBody)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(MOCK_UPDATE_CODE))
                .andExpect(jsonPath("$.title").value(MOCK_UPDATE_TITLE));
    }

    @Test
    void updateServiceWithInvalidBodyTest() throws Exception {

        ServiceDto updateBody = TestServiceDataUtil.createUpdateServiceDto();
        updateBody.setId(null);
        updateBody.setCode("code");
        updateBody.setTitle("title11");
        when(servicesService.updateService(MOCK_ID,updateBody)).thenReturn(updateBody);

        mockMvc.perform(
                        patch("/api/v1/services/" + MOCK_ID)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(updateBody)))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()))
                .andExpect(jsonPath("$[1].errorType").value(ErrorType.VALIDATION_ERROR_TYPE.name()));
    }

    @Test
    void deleteServiceTest() throws Exception {

        doNothing().when(servicesService).deleteService(MOCK_ID);

        mockMvc.perform(
                delete("/api/v1/services/" + MOCK_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    //Exceptions

    @Test
    void getServiceById_ServiceNotFoundTest() throws Exception {

        when(servicesService.getService(MOCK_ID)).thenThrow(new ServiceNotFoundException());

        mockMvc.perform(
                        get("/api/v1/services/"+ MOCK_ID ))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(SERVICE_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }

    @Test
    void updateService_ServiceNotFoundTest() throws Exception {
        ServiceDto updateBody = TestServiceDataUtil.createUpdateServiceDto();
        updateBody.setId(null);
        when(servicesService.updateService(MOCK_ID,updateBody)).thenThrow(new ServiceNotFoundException());

        mockMvc.perform(
                        patch("/api/v1/services/" + MOCK_ID)
                                .contentType("application/json")
                                .content(objectMapper.writeValueAsString(updateBody)))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(SERVICE_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }

    @Test
    void deleteService_ServiceNotFoundTest() throws Exception {

        doThrow(new ServiceNotFoundException()).when(servicesService).deleteService(MOCK_ID);

        mockMvc.perform(
                delete("/api/v1/services/" + MOCK_ID))
                .andDo(print())
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(SERVICE_NOT_FOUND))
                .andExpect(jsonPath("$.errorType").value(ErrorType.DATABASE_ERROR_TYPE.name()));
    }
}
