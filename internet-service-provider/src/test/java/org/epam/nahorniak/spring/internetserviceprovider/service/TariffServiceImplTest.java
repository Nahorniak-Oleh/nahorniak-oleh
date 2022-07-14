package org.epam.nahorniak.spring.internetserviceprovider.service;

import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.TariffDto;
import org.epam.nahorniak.spring.internetserviceprovider.exception.ServiceNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.exception.TariffNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.model.ServiceModel;
import org.epam.nahorniak.spring.internetserviceprovider.model.Tariff;
import org.epam.nahorniak.spring.internetserviceprovider.repository.ServiceRepository;
import org.epam.nahorniak.spring.internetserviceprovider.repository.TariffRepository;
import org.epam.nahorniak.spring.internetserviceprovider.service.impl.TariffServiceImpl;
import org.epam.nahorniak.spring.internetserviceprovider.service.update.impl.TariffUpdateServiceImpl;
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

    private static Tariff expectedTariff;

    private static final Long MOCK_ID = 1L;
    private static final String MOCK_CODE = "#STANDARD";
    private static final String MOCK_TITLE = "Standard";
    private static final Double MOCK_PRICE = 5.5;

    private static final String UPDATE_MOCK_CODE = "#FAST";
    private static final String UPDATE_MOCK_TITLE = "Fast";
    private static final Double UPDATE_MOCK_PRICE = 3.5;

    private static final Integer PAGE = 0;
    private static final Integer SIZE = 1;

    @BeforeEach
    void initEach() {
        expectedTariff = Tariff.builder().id(MOCK_ID).code(MOCK_CODE)
                .title(MOCK_TITLE).price(MOCK_PRICE).services(new HashSet<>())
                .build();
    }

    @Test
    void listTariffsTest(){
        //given
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
        TariffDto tariffDto = TariffDto.builder().code(MOCK_CODE)
                .title(MOCK_TITLE).price(MOCK_PRICE).build();
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
        TariffDto updateBody = TariffDto.builder().id(MOCK_ID).code(UPDATE_MOCK_CODE)
                .title(UPDATE_MOCK_TITLE).price(UPDATE_MOCK_PRICE).build();
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
        ServiceModel serviceModel = mock(ServiceModel.class);
        when(serviceRepository.findServiceById(MOCK_ID)).thenReturn(Optional.of(serviceModel));
        when(tariffRepository.findTariffById(MOCK_ID)).thenReturn(Optional.of(expectedTariff));
        when(tariffRepository.save(any())).thenReturn(expectedTariff);

        //when
        TariffDto tariffDto = tariffService.addService(MOCK_ID,MOCK_ID);

        //then
        assertThat(tariffDto.getServices(), hasSize(1));
    }

    @Test
    void removeServiceTest(){
        //given
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
        when(serviceRepository.findServiceById(MOCK_ID)).thenReturn(Optional.empty());
        when(tariffRepository.findTariffById(MOCK_ID)).thenReturn(Optional.of(expectedTariff));
        assertThrows(ServiceNotFoundException.class, () -> tariffService.addService(MOCK_ID,MOCK_ID));
    }
}
