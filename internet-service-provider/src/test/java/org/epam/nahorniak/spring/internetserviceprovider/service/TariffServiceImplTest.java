package org.epam.nahorniak.spring.internetserviceprovider.service;

import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.ServiceDto;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.TariffDto;
import org.epam.nahorniak.spring.internetserviceprovider.exception.ServiceNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.exception.TariffNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.model.ServiceModel;
import org.epam.nahorniak.spring.internetserviceprovider.model.Tariff;
import org.epam.nahorniak.spring.internetserviceprovider.repository.ServiceRepository;
import org.epam.nahorniak.spring.internetserviceprovider.repository.TariffRepository;
import org.epam.nahorniak.spring.internetserviceprovider.service.impl.TariffServiceImpl;
import org.epam.nahorniak.spring.internetserviceprovider.service.update.impl.TariffUpdateServiceImpl;
import org.epam.nahorniak.spring.internetserviceprovider.util.TestServiceDataUtil;
import org.epam.nahorniak.spring.internetserviceprovider.util.TestTariffDataUtil;
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

import static org.epam.nahorniak.spring.internetserviceprovider.util.TestTariffDataUtil.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class TariffServiceImplTest {

    @InjectMocks
    private TariffServiceImpl tariffService;

    @Spy
    private TariffUpdateServiceImpl updateService;
    @Mock
    private TariffRepository tariffRepository;
    @Mock
    private ServiceRepository serviceRepository;

    private static final Integer PAGE = 0;
    private static final Integer SIZE = 1;


    @Test
    void listTariffsTest(){
        //given
        Tariff expectedTariff = TestTariffDataUtil.createTariff();
        PageRequest pageRequest = PageRequest.of(PAGE, SIZE, Sort.by("price").descending());
        List<Tariff> tariffList = Collections.singletonList(expectedTariff);
        Page<Tariff> tariffPage = new PageImpl<>(tariffList);
        when(tariffRepository.findAll(pageRequest)).thenReturn(tariffPage);

        //when
        List<TariffDto> tariffs = tariffService.listTariffs(PAGE,SIZE);

        //then
        assertThat(tariffs, hasSize(1));
    }

    @Test
    void getTariffTest() {
        //given
        Tariff expectedTariff = TestTariffDataUtil.createTariff();
        when(tariffRepository.findTariffById(MOCK_ID)).thenReturn(Optional.of(expectedTariff));

        //when
        TariffDto tariffDto = tariffService.getTariff(MOCK_ID);

        //then
        assertThat(tariffDto,allOf(
                hasProperty("id",equalTo(expectedTariff.getId())),
                hasProperty("code",equalTo(expectedTariff.getCode()))
        ));
    }

    @Test
    void createTariffTest(){
        //given
        Tariff expectedTariff = TestTariffDataUtil.createTariff();
        TariffDto tariffDto = TestTariffDataUtil.createTariffDto();
        when(tariffRepository.save(any())).thenReturn(expectedTariff);

        //when
        tariffDto = tariffService.createTariff(tariffDto);

        //then
        assertThat(tariffDto,allOf(
                hasProperty("title",equalTo(expectedTariff.getTitle())),
                hasProperty("price",equalTo(expectedTariff.getPrice()))
        ));
    }

    @Test
    void updateTariffTest(){
        //given
        Tariff expectedTariff = TestTariffDataUtil.createTariff();
        TariffDto updateBody = TestTariffDataUtil.createUpdatedTariffDto();
        when(tariffRepository.findTariffById(MOCK_ID)).thenReturn(Optional.of(expectedTariff));
        when(tariffRepository.save(any())).thenReturn(expectedTariff);

        //when
        updateBody = tariffService.updateTariff(MOCK_ID,updateBody);

        //then
        assertThat(updateBody, allOf(
                hasProperty("code",equalTo(expectedTariff.getCode())),
                hasProperty("title",equalTo(expectedTariff.getTitle())),
                hasProperty("price",equalTo(expectedTariff.getPrice()))
        ));
    }

    @Test
    void deleteTariffTest(){
        //given
        Tariff expectedTariff = TestTariffDataUtil.createTariff();
        when(tariffRepository.findTariffById(MOCK_ID)).thenReturn(Optional.of(expectedTariff));
        doNothing().when(tariffRepository).delete(any());

        //when
        tariffService.deleteTariff(MOCK_ID);

        //then
        verify(tariffRepository, times(1)).delete(expectedTariff);
    }

    @Test
    void addServiceTest(){
        //given
        Tariff expectedTariff = TestTariffDataUtil.createTariff();
        ServiceModel serviceModel = TestServiceDataUtil.createService();
        when(serviceRepository.findServiceById(MOCK_ID)).thenReturn(Optional.of(serviceModel));
        when(tariffRepository.findTariffById(MOCK_ID)).thenReturn(Optional.of(expectedTariff));
        when(tariffRepository.save(any())).thenReturn(expectedTariff);

        //when
        TariffDto tariffDto = tariffService.addService(MOCK_ID,MOCK_ID);

        //then
        List<ServiceDto> serviceDtoList = tariffDto.getServices();
        assertThat(serviceDtoList,hasSize(1));
        assertThat(serviceDtoList.get(0),allOf(
                hasProperty("id",equalTo(serviceModel.getId())),
                hasProperty("code",equalTo(serviceModel.getCode())),
                hasProperty("title",equalTo(serviceModel.getTitle()))
        ));
    }

    @Test
    void removeServiceTest(){
        //given
        Tariff expectedTariff = TestTariffDataUtil.createTariff();
        ServiceModel serviceModel = mock(ServiceModel.class);
        when(serviceRepository.findServiceById(MOCK_ID)).thenReturn(Optional.of(serviceModel));
        when(tariffRepository.findTariffById(MOCK_ID)).thenReturn(Optional.of(expectedTariff));
        when(tariffRepository.save(any())).thenReturn(expectedTariff);

        //when
        TariffDto tariffDto = tariffService.removeService(MOCK_ID,MOCK_ID);

        //then
        assertThat(tariffDto.getServices(), hasSize(0));
    }


    @Test
    void getTariffNotFoundTest(){
        when(tariffRepository.findTariffById(MOCK_ID)).thenReturn(Optional.empty());
        assertThrows(TariffNotFoundException.class, () -> tariffService.getTariff(MOCK_ID));
    }

    @Test
    void updateTariffNotFoundTest(){
        TariffDto updateBody = TariffDto.builder().id(MOCK_ID).build();
        when(tariffRepository.findTariffById(MOCK_ID)).thenReturn(Optional.empty());
        assertThrows(TariffNotFoundException.class, () -> tariffService.updateTariff(MOCK_ID,updateBody));
    }

    @Test
    void deleteTariffNotFoundTest(){
        when(tariffRepository.findTariffById(MOCK_ID)).thenReturn(Optional.empty());
        assertThrows(TariffNotFoundException.class, () -> tariffService.deleteTariff(MOCK_ID));
    }

    @Test
    void addServiceTariffNotFoundTest(){
        when(tariffRepository.findTariffById(MOCK_ID)).thenReturn(Optional.empty());
        assertThrows(TariffNotFoundException.class, () -> tariffService.addService(MOCK_ID,MOCK_ID));
    }

    @Test
    void addServiceNotFoundTest(){
        Tariff expectedTariff = TestTariffDataUtil.createTariff();
        when(serviceRepository.findServiceById(MOCK_ID)).thenReturn(Optional.empty());
        when(tariffRepository.findTariffById(MOCK_ID)).thenReturn(Optional.of(expectedTariff));
        assertThrows(ServiceNotFoundException.class, () -> tariffService.addService(MOCK_ID,MOCK_ID));
    }

    @Test
    void removeServiceTariffNotFoundTest(){
        when(tariffRepository.findTariffById(MOCK_ID)).thenReturn(Optional.empty());
        assertThrows(TariffNotFoundException.class, () -> tariffService.addService(MOCK_ID,MOCK_ID));
    }

    @Test
    void removeServiceNotFoundTest(){
        Tariff expectedTariff = TestTariffDataUtil.createTariff();
        when(serviceRepository.findServiceById(MOCK_ID)).thenReturn(Optional.empty());
        when(tariffRepository.findTariffById(MOCK_ID)).thenReturn(Optional.of(expectedTariff));
        assertThrows(ServiceNotFoundException.class, () -> tariffService.addService(MOCK_ID,MOCK_ID));
    }
}
