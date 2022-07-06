package org.epam.nahorniak.spring.homework_3.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.epam.nahorniak.spring.homework_3.controller.dto.RequestDto;
import org.epam.nahorniak.spring.homework_3.mapper.RequestMapper;
import org.epam.nahorniak.spring.homework_3.model.Request;
import org.epam.nahorniak.spring.homework_3.repository.RequestRepository;
import org.epam.nahorniak.spring.homework_3.service.RequestService;
import org.epam.nahorniak.spring.homework_3.service.TariffService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final TariffService tariffService;

    @Override
    public RequestDto createRequest(String email, int tariffId) {
        log.info("RequestService --> create request with user email ({}) and tariff id - ({})", email, tariffId);
        Request request = requestRepository.createRequest(email, tariffId);
        return mapRequestAndSetTariff(request);
    }

    @Override
    public RequestDto closeRequest(String email) {
        log.info("RequestService --> close request by user email {}", email);
        Request request = requestRepository.closeRequest(email);
        return mapRequestAndSetTariff(request);
    }

    @Override
    public RequestDto activateRequest(String email) {
        log.info("RequestService --> activate request by user email {}", email);
        Request request = requestRepository.activateRequest(email);
        return mapRequestAndSetTariff(request);
    }

    @Override
    public RequestDto suspendRequest(String email) {
        log.info("RequestService --> suspend request by user email {}", email);
        Request request = requestRepository.suspendRequest(email);
        return mapRequestAndSetTariff(request);
    }

    @Override
    public List<RequestDto> getAllByUserEmail(String email) {
        log.info("RequestService --> get all requests by user email {}", email);
        List<Request> requestList = requestRepository.getAllByUserEmail(email);
        List<RequestDto> requestDtoList = new ArrayList<>();
        requestList.forEach(request -> {
            RequestDto requestDto = RequestMapper.INSTANCE.mapRequestToRequestDto(request);
            requestDto.setTariffDto(tariffService.getTariff(request.getTariffId()));
            requestDtoList.add(requestDto);
        });
        return requestDtoList;
    }

    @Override
    public RequestDto getActiveOrSuspendedRequestByUserEmail(String email) {
        log.info("RequestService --> get active or suspended request by user email {}", email);
        Request request = requestRepository.getActiveOrSuspendedRequestByUserEmail(email);
        return mapRequestAndSetTariff(request);
    }

    private RequestDto mapRequestAndSetTariff(Request request) {
        log.info("RequestService --> map request {} to requestDto and set tariff for it ", request);
        RequestDto requestDto = RequestMapper.INSTANCE.mapRequestToRequestDto(request);
        requestDto.setTariffDto(tariffService.getTariff(request.getTariffId()));
        return requestDto;
    }
}
