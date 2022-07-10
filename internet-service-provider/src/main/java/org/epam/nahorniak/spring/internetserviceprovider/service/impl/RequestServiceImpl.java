package org.epam.nahorniak.spring.internetserviceprovider.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.RequestDto;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.TariffDto;
import org.epam.nahorniak.spring.internetserviceprovider.exception.RequestNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.exception.TariffNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.exception.UserNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.mapper.RequestMapper;
import org.epam.nahorniak.spring.internetserviceprovider.mapper.TariffMapper;
import org.epam.nahorniak.spring.internetserviceprovider.model.Request;
import org.epam.nahorniak.spring.internetserviceprovider.model.Tariff;
import org.epam.nahorniak.spring.internetserviceprovider.model.User;
import org.epam.nahorniak.spring.internetserviceprovider.model.enums.RequestStatus;
import org.epam.nahorniak.spring.internetserviceprovider.repository.RequestRepository;
import org.epam.nahorniak.spring.internetserviceprovider.repository.TariffRepository;
import org.epam.nahorniak.spring.internetserviceprovider.repository.UserRepository;
import org.epam.nahorniak.spring.internetserviceprovider.service.RequestService;
import org.epam.nahorniak.spring.internetserviceprovider.service.TariffService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final TariffRepository tariffRepository;
    private final UserRepository userRepository;

    @Override
    public RequestDto createRequest(String email, Long tariffId) {
        log.info("RequestService --> create request with user email ({}) and tariff id - ({})", email, tariffId);
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Tariff tariff = tariffRepository.findTariffById(tariffId).orElseThrow(TariffNotFoundException::new);

        LocalDate now = LocalDate.now();

        Request request =
                Request.builder()
                        .user(user)
                        .tariff(tariff)
                        .startDate(now)
                        .endDate(now.plusMonths(1))
                        .status(RequestStatus.ACTIVE)
                        .build();

        request = requestRepository.save(request);
        RequestDto requestDto = RequestMapper.INSTANCE.mapRequestToRequestDto(request);
        TariffDto tariffDto = TariffMapper.INSTANCE.mapTariffToTariffDto(request.getTariff());
        requestDto.setTariffDto(tariffDto);
        return requestDto;
    }

    @Override
    public RequestDto closeRequest(String email) {
        log.info("RequestService --> close request by user email {}", email);
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Request request = requestRepository.getRequestByUserAndStatus(user).orElseThrow(RequestNotFoundException::new);
        request.setStatus(RequestStatus.CLOSED);
        request = requestRepository.save(request);
        RequestDto requestDto = RequestMapper.INSTANCE.mapRequestToRequestDto(request);
        TariffDto tariffDto = TariffMapper.INSTANCE.mapTariffToTariffDto(request.getTariff());
        requestDto.setTariffDto(tariffDto);
        return requestDto;
    }

    @Override
    public RequestDto activateRequest(String email) {
        log.info("RequestService --> activate request by user email {}", email);
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Request request = requestRepository.getRequestByUserAndStatus(user).orElseThrow(RequestNotFoundException::new);
        request.setStatus(RequestStatus.ACTIVE);
        request = requestRepository.save(request);
        RequestDto requestDto = RequestMapper.INSTANCE.mapRequestToRequestDto(request);
        TariffDto tariffDto = TariffMapper.INSTANCE.mapTariffToTariffDto(request.getTariff());
        requestDto.setTariffDto(tariffDto);
        return requestDto;
    }

    @Override
    public RequestDto suspendRequest(String email) {
        log.info("RequestService --> suspend request by user email {}", email);
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Request request = requestRepository.getRequestByUserAndStatus(user).orElseThrow(RequestNotFoundException::new);
        request.setStatus(RequestStatus.SUSPENDED);
        request = requestRepository.save(request);
        RequestDto requestDto = RequestMapper.INSTANCE.mapRequestToRequestDto(request);
        TariffDto tariffDto = TariffMapper.INSTANCE.mapTariffToTariffDto(request.getTariff());
        requestDto.setTariffDto(tariffDto);
        return requestDto;
    }

    @Override
    public List<RequestDto> getAllByUserEmail(String email) {
        log.info("RequestService --> get all requests by user email {}", email);
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        List<Request> requestList = requestRepository.findAllByUser(user);
        List <RequestDto> requestDtoList = new ArrayList<>();
        requestList.stream().forEach(request -> {
            RequestDto requestDto = RequestMapper.INSTANCE.mapRequestToRequestDto(request);
            TariffDto tariffDto = TariffMapper.INSTANCE.mapTariffToTariffDto(request.getTariff());
            requestDto.setTariffDto(tariffDto);
            requestDtoList.add(requestDto);
        });
        return requestDtoList;
    }

    @Override
    public RequestDto getActiveOrSuspendedRequestByUserEmail(String email) {
        log.info("RequestService --> get active or suspended request by user email {}", email);
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        Request request = requestRepository.getRequestByUserAndStatus(user).orElseThrow(RequestNotFoundException::new);
        RequestDto requestDto = RequestMapper.INSTANCE.mapRequestToRequestDto(request);
        TariffDto tariffDto = TariffMapper.INSTANCE.mapTariffToTariffDto(request.getTariff());
        requestDto.setTariffDto(tariffDto);
        return requestDto;
    }

}
