package org.epam.nahorniak.spring.internetserviceprovider.service;

import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.RequestDto;

import java.util.List;

public interface RequestService {

    RequestDto createRequest(String email, int tariffId);

    RequestDto closeRequest(String email);

    RequestDto activateRequest(String email);

    RequestDto suspendRequest(String email);

    List<RequestDto> getAllByUserEmail(String email);

    RequestDto getActiveOrSuspendedRequestByUserEmail(String email);
}
