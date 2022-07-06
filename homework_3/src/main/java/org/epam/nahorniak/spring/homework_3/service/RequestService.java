package org.epam.nahorniak.spring.homework_3.service;

import org.epam.nahorniak.spring.homework_3.controller.dto.RequestDto;
import org.epam.nahorniak.spring.homework_3.model.Request;

import java.util.List;

public interface RequestService {

    RequestDto createRequest(String email, int tariffId);

    RequestDto closeRequest(String email);

    RequestDto activateRequest(String email);

    RequestDto suspendRequest(String email);

    List<RequestDto> getAllByUserEmail(String email);

    RequestDto getActiveOrSuspendedRequestByUserEmail(String email);
}
