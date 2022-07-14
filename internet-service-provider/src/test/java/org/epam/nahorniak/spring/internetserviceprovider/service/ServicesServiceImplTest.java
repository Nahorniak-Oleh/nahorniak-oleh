package org.epam.nahorniak.spring.internetserviceprovider.service;

import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.ServiceDto;
import org.epam.nahorniak.spring.internetserviceprovider.exception.ServiceNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.exception.TariffNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.model.ServiceModel;
import org.epam.nahorniak.spring.internetserviceprovider.model.Tariff;
import org.epam.nahorniak.spring.internetserviceprovider.repository.ServiceRepository;
import org.epam.nahorniak.spring.internetserviceprovider.repository.TariffRepository;
import org.epam.nahorniak.spring.internetserviceprovider.service.impl.ServicesServiceImpl;
import org.epam.nahorniak.spring.internetserviceprovider.service.update.impl.ServiceUpdateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ServicesServiceImplTest {

    @InjectMocks
    private ServicesServiceImpl servicesService;

    @Spy
    private ServiceUpdateServiceImpl updateService;
    @Mock
    private ServiceRepository serviceRepository;
    @Mock
    private TariffRepository tariffRepository;

    private static ServiceModel expectedService;

    private static final Long MOCK_ID = 1L;
    private static final String MOCK_CODE = "#INTERNET";
    private static final String MOCK_TITLE = "Internet";

    private static final String MOCK_UPDATE_CODE = "#CELL";
    private static final String MOCK_UPDATE_TITLE = "Cell";

    private static final Integer PAGE = 0;
    private static final Integer SIZE = 1;


    @BeforeEach
    void initEach(){
        expectedService = ServiceModel.builder().id(MOCK_ID)
                .code(MOCK_CODE).title(MOCK_TITLE)
                .tariffs(new HashSet<>()).build();
    }

    @Test
    void listServicesTest(){
        //given
        PageRequest pageRequest = PageRequest.of(PAGE,SIZE, Sort.by("id").ascending());
        List<ServiceModel> serviceModelList = Collections.singletonList(expectedService);
        Page<ServiceModel> serviceModelPage = new PageImpl<>(serviceModelList);
        when(serviceRepository.findAll(pageRequest)).thenReturn(serviceModelPage);

        //when
        List<ServiceDto> services = servicesService.listServices(PAGE,SIZE);

        //then
        assertThat(services, hasSize(1));
    }

    @Test
    void getServiceTest(){
        //given
        when(serviceRepository.findServiceById(MOCK_ID)).thenReturn(Optional.of(expectedService));

        //when
        ServiceDto service = servicesService.getService(MOCK_ID);

        //then
        assertThat(service,allOf(
                hasProperty("id",equalTo(expectedService.getId())),
                hasProperty("code",equalTo(expectedService.getCode()))
        ));
    }

    @Test
    void createServiceTest(){
        //given
        ServiceDto createBody = ServiceDto.builder().code(MOCK_CODE).title(MOCK_CODE).build();
        when(serviceRepository.save(any())).thenReturn(expectedService);

        //when
        createBody = servicesService.createService(createBody);

        //then
        assertThat(createBody,allOf(
                hasProperty("id",equalTo(expectedService.getId())),
                hasProperty("code",equalTo(expectedService.getCode()))
        ));
    }

    @Test
    void updateServiceTest(){
        //given
        ServiceDto updateBody = ServiceDto.builder().code(MOCK_UPDATE_CODE).title(MOCK_UPDATE_TITLE).build();
        when(serviceRepository.findServiceById(MOCK_ID)).thenReturn(Optional.of(expectedService));
        when(serviceRepository.save(any())).thenReturn(expectedService);

        //when
        updateBody = servicesService.updateService(MOCK_ID,updateBody);

        //then
        assertThat(updateBody,allOf(
                hasProperty("code",equalTo(expectedService.getCode())),
                hasProperty("title",equalTo(expectedService.getTitle()))
        ));
    }

    @Test
    void deleteServiceTest(){
        //given
        when(serviceRepository.findServiceById(MOCK_ID)).thenReturn(Optional.of(expectedService));
        doNothing().when(serviceRepository).delete(any());

        //when
        servicesService.deleteService(MOCK_ID);

        //then
        verify(serviceRepository, times(1)).delete(expectedService);
    }

    @Test
    void getAllByTariffIdTest(){
        //given
        Tariff tariff = Tariff.builder().id(MOCK_ID).code(MOCK_CODE).title(MOCK_TITLE)
                .services(Set.of(expectedService)).build();
        when(tariffRepository.findTariffById(MOCK_ID)).thenReturn(Optional.of(tariff));

        //when
        Set<ServiceDto> services = servicesService.getAllByTariffId(MOCK_ID);

        //then
        assertThat(services, hasSize(1));
    }

    @Test
    void getServiceNotFoundTest(){
        when(serviceRepository.findServiceById(MOCK_ID)).thenReturn(Optional.empty());
        assertThrows(ServiceNotFoundException.class, ()-> servicesService.getService(MOCK_ID));
    }

    @Test
    void updateServiceNotFoundTest(){
        ServiceDto updateBody = ServiceDto.builder().code(MOCK_UPDATE_CODE).title(MOCK_UPDATE_TITLE).build();
        when(serviceRepository.findServiceById(MOCK_ID)).thenReturn(Optional.empty());
        assertThrows(ServiceNotFoundException.class, ()-> servicesService.updateService(MOCK_ID,updateBody));
    }

    @Test
    void deleteServiceNotFoundTest(){
        when(serviceRepository.findServiceById(MOCK_ID)).thenReturn(Optional.empty());
        assertThrows(ServiceNotFoundException.class, ()-> servicesService.deleteService(MOCK_ID));
    }

    @Test
    void getAllByTariffIdNotFoundTest(){
        when(tariffRepository.findTariffById(MOCK_ID)).thenReturn(Optional.empty());
        assertThrows(TariffNotFoundException.class, ()-> servicesService.getAllByTariffId(MOCK_ID));
    }
}
