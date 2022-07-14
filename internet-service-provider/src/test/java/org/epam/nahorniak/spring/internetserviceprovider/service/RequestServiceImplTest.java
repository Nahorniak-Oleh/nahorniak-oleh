package org.epam.nahorniak.spring.internetserviceprovider.service;

import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.RequestDto;
import org.epam.nahorniak.spring.internetserviceprovider.exception.RequestNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.exception.TariffNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.exception.UserNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.model.Request;
import org.epam.nahorniak.spring.internetserviceprovider.model.Tariff;
import org.epam.nahorniak.spring.internetserviceprovider.model.User;
import org.epam.nahorniak.spring.internetserviceprovider.model.enums.RequestStatus;
import org.epam.nahorniak.spring.internetserviceprovider.repository.RequestRepository;
import org.epam.nahorniak.spring.internetserviceprovider.repository.TariffRepository;
import org.epam.nahorniak.spring.internetserviceprovider.repository.UserRepository;
import org.epam.nahorniak.spring.internetserviceprovider.service.impl.RequestServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RequestServiceImplTest {

    @InjectMocks
    private RequestServiceImpl requestService;

    @Mock
    private RequestRepository requestRepository;
    @Mock
    private TariffRepository tariffRepository;
    @Mock
    private UserRepository userRepository;

    private static Request expectedRequest;
    private static User expectedUser;
    private static Tariff expectedTariff;

    private static final Long MOCK_ID = 1L;
    private static final LocalDate MOCK_START_DATE = LocalDate.now();
    private static final LocalDate MOCK_END_DATE = MOCK_START_DATE.plusMonths(1);
    private static final RequestStatus MOCK_STATUS = RequestStatus.ACTIVE;
    private final static String MOCK_EMAIL = "EMAIL@gmail.com";

    private static final Integer PAGE = 0;
    private static final Integer SIZE = 1;

    @BeforeEach
    void initEach(){
        expectedUser = User.builder().email(MOCK_EMAIL).build();
        expectedTariff = Tariff.builder().id(MOCK_ID).build();
        expectedRequest = Request.builder().id(MOCK_ID).user(expectedUser).tariff(expectedTariff)
                .startDate(MOCK_START_DATE).endDate(MOCK_END_DATE).status(MOCK_STATUS).build();
    }

    @Test
    void createRequestTest(){
        //given
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedUser));
        when(tariffRepository.findTariffById(MOCK_ID)).thenReturn(Optional.of(expectedTariff));
        when(requestRepository.save(any())).thenReturn(expectedRequest);

        //when
        RequestDto requestDto = requestService.createRequest(MOCK_EMAIL,MOCK_ID);

        //then
        assertThat(requestDto,allOf(
                hasProperty("id",equalTo(expectedRequest.getId())),
                hasProperty("startDate",equalTo(expectedRequest.getStartDate())),
                hasProperty("endDate",equalTo(expectedRequest.getEndDate())),
                hasProperty("tariffDto")
        ));
    }

    @Test
    void closeRequestTest(){
        //given
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedUser));
        when(requestRepository.getRequestByUserAndStatus(expectedUser)).thenReturn(Optional.of(expectedRequest));
        when(requestRepository.save(any())).thenReturn(expectedRequest);

        //when
        RequestDto requestDto = requestService.closeRequest(MOCK_EMAIL);

        //then
        assertThat(requestDto.getStatus(),equalTo(RequestStatus.CLOSED));
    }

    @Test
    void activateRequestTest(){
        //given
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedUser));
        when(requestRepository.getRequestByUserAndStatus(expectedUser)).thenReturn(Optional.of(expectedRequest));
        when(requestRepository.save(any())).thenReturn(expectedRequest);

        //when
        RequestDto requestDto = requestService.activateRequest(MOCK_EMAIL);

        //then
        assertThat(requestDto.getStatus(),equalTo(RequestStatus.ACTIVE));
    }

    @Test
    void suspendRequestTest(){
        //given
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedUser));
        when(requestRepository.getRequestByUserAndStatus(expectedUser)).thenReturn(Optional.of(expectedRequest));
        when(requestRepository.save(any())).thenReturn(expectedRequest);

        //when
        RequestDto requestDto = requestService.suspendRequest(MOCK_EMAIL);

        //then
        assertThat(requestDto.getStatus(),equalTo(RequestStatus.SUSPENDED));
    }

    @Test
    void getAllByUserEmailTest(){
        //given
        PageRequest pageRequest = PageRequest.of(PAGE,SIZE, Sort.by("startDate").ascending());
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedUser));
        when(requestRepository.findAllByUser(expectedUser,pageRequest))
                .thenReturn(Collections.singletonList(expectedRequest));

        //when
        List<RequestDto> requests = requestService.getAllByUserEmail(MOCK_EMAIL,PAGE,SIZE);

        //then
        assertThat(requests, hasSize(1));
    }

    @Test
    void getActiveOrSuspendedRequestByUserEmailTest(){
        //given
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedUser));
        when(requestRepository.getRequestByUserAndStatus(expectedUser)).thenReturn(Optional.of(expectedRequest));

        //when
        RequestDto requestDto = requestService.getActiveOrSuspendedRequestByUserEmail(MOCK_EMAIL);

        //then
        assertThat(requestDto.getStatus(),anyOf(
                equalTo(RequestStatus.SUSPENDED),
                equalTo(RequestStatus.ACTIVE)
        ));
    }

    @Test
    void createRequestUserNotFoundTest(){
        assertThrows(UserNotFoundException.class, () -> requestService.createRequest(MOCK_EMAIL,MOCK_ID));
    }

    @Test
    void createRequestTariffNotFoundTest(){
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedUser));
        assertThrows(TariffNotFoundException.class, () -> requestService.createRequest(MOCK_EMAIL,MOCK_ID));
    }

    @Test
    void closeRequestUserNotFoundTest(){
        assertThrows(UserNotFoundException.class, () -> requestService.closeRequest(MOCK_EMAIL));
    }

    @Test
    void closeRequestRequestNotFoundTest(){
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedUser));
        assertThrows(RequestNotFoundException.class, () -> requestService.closeRequest(MOCK_EMAIL));
    }

    @Test
    void activateRequestUserNotFoundTest(){
        assertThrows(UserNotFoundException.class, () -> requestService.activateRequest(MOCK_EMAIL));
    }

    @Test
    void activateRequestRequestNotFoundTest(){
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedUser));
        assertThrows(RequestNotFoundException.class, () -> requestService.activateRequest(MOCK_EMAIL));
    }

    @Test
    void suspendRequestUserNotFoundTest(){
        assertThrows(UserNotFoundException.class, () -> requestService.suspendRequest(MOCK_EMAIL));
    }

    @Test
    void suspendRequestRequestNotFoundTest(){
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedUser));
        assertThrows(RequestNotFoundException.class, () -> requestService.suspendRequest(MOCK_EMAIL));
    }

    @Test
    void getAllByUserEmailUserNotFoundTest(){
        assertThrows(UserNotFoundException.class, () -> requestService.getAllByUserEmail(MOCK_EMAIL,PAGE,SIZE));
    }

    @Test
    void getActiveOrSuspendedRequestByUserEmailUserNotFoundTest(){
        assertThrows(UserNotFoundException.class, () -> requestService.suspendRequest(MOCK_EMAIL));
    }

    @Test
    void getActiveOrSuspendedRequestByUserEmailRequestNotFoundTest(){
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedUser));
        assertThrows(RequestNotFoundException.class, () -> requestService.suspendRequest(MOCK_EMAIL));
    }
}
