package org.epam.nahorniak.spring.internetserviceprovider.service;

import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.RequestDto;
import org.epam.nahorniak.spring.internetserviceprovider.exception.RequestNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.exception.TariffNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.exception.UserNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.model.Request;
import org.epam.nahorniak.spring.internetserviceprovider.model.User;
import org.epam.nahorniak.spring.internetserviceprovider.model.enums.RequestStatus;
import org.epam.nahorniak.spring.internetserviceprovider.repository.RequestRepository;
import org.epam.nahorniak.spring.internetserviceprovider.repository.TariffRepository;
import org.epam.nahorniak.spring.internetserviceprovider.repository.UserRepository;
import org.epam.nahorniak.spring.internetserviceprovider.service.impl.RequestServiceImpl;
import org.epam.nahorniak.spring.internetserviceprovider.util.TestRequestDataUtil;
import org.epam.nahorniak.spring.internetserviceprovider.util.TestUserDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.epam.nahorniak.spring.internetserviceprovider.util.TestRequestDataUtil.MOCK_EMAIL;
import static org.epam.nahorniak.spring.internetserviceprovider.util.TestRequestDataUtil.MOCK_ID;
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

    private static final Integer PAGE = 0;
    private static final Integer SIZE = 1;

    @Test
    void createRequestTest(){
        //given
        Request expectedRequest = TestRequestDataUtil.createRequest();
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedRequest.getUser()));
        when(tariffRepository.findTariffById(MOCK_ID)).thenReturn(Optional.of(expectedRequest.getTariff()));
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
        Request expectedRequest = TestRequestDataUtil.createRequest();
        User expectedUser = expectedRequest.getUser();
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
        Request expectedRequest = TestRequestDataUtil.createRequest();
        User expectedUser = expectedRequest.getUser();
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
        Request expectedRequest = TestRequestDataUtil.createRequest();
        User expectedUser = expectedRequest.getUser();
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
        Request expectedRequest = TestRequestDataUtil.createRequest();
        User expectedUser = expectedRequest.getUser();
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
        Request expectedRequest = TestRequestDataUtil.createRequest();
        User expectedUser = expectedRequest.getUser();
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
        User expectedUser = TestUserDataUtil.createUser();
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedUser));
        assertThrows(TariffNotFoundException.class, () -> requestService.createRequest(MOCK_EMAIL,MOCK_ID));
    }

    @Test
    void closeRequestUserNotFoundTest(){
        assertThrows(UserNotFoundException.class, () -> requestService.closeRequest(MOCK_EMAIL));
    }

    @Test
    void closeRequestRequestNotFoundTest(){
        User expectedUser = TestUserDataUtil.createUser();
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedUser));
        assertThrows(RequestNotFoundException.class, () -> requestService.closeRequest(MOCK_EMAIL));
    }

    @Test
    void activateRequestUserNotFoundTest(){
        assertThrows(UserNotFoundException.class, () -> requestService.activateRequest(MOCK_EMAIL));
    }

    @Test
    void activateRequestRequestNotFoundTest(){
        User expectedUser = TestUserDataUtil.createUser();
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedUser));
        assertThrows(RequestNotFoundException.class, () -> requestService.activateRequest(MOCK_EMAIL));
    }

    @Test
    void suspendRequestUserNotFoundTest(){
        assertThrows(UserNotFoundException.class, () -> requestService.suspendRequest(MOCK_EMAIL));
    }

    @Test
    void suspendRequestRequestNotFoundTest(){
        User expectedUser = TestUserDataUtil.createUser();
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
        User expectedUser = TestUserDataUtil.createUser();
        when(userRepository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(expectedUser));
        assertThrows(RequestNotFoundException.class, () -> requestService.suspendRequest(MOCK_EMAIL));
    }
}
