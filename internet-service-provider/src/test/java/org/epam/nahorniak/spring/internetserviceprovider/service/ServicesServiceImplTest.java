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
import org.epam.nahorniak.spring.internetserviceprovider.testUtils.TestServiceDataUtil;
import org.epam.nahorniak.spring.internetserviceprovider.testUtils.TestTariffDataUtil;
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

import static org.epam.nahorniak.spring.internetserviceprovider.testUtils.TestServiceDataUtil.*;
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

    private static final Integer PAGE = 0;
    private static final Integer SIZE = 1;

    @Test
    void listServicesTest(){
        //given
        ServiceModel expectedService = TestServiceDataUtil.createService();
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
        ServiceModel expectedService = TestServiceDataUtil.createService();
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
        ServiceModel expectedService = TestServiceDataUtil.createService();
        ServiceDto createBody = TestServiceDataUtil.createServiceDto();
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
        ServiceModel expectedService = TestServiceDataUtil.createService();
        ServiceDto updateBody = TestServiceDataUtil.createUpdateServiceDto();
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
        ServiceModel expectedService = TestServiceDataUtil.createService();
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
        ServiceModel expectedService = TestServiceDataUtil.createService();
        Tariff tariff = TestTariffDataUtil.createTariff();
        tariff.addService(expectedService);
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
