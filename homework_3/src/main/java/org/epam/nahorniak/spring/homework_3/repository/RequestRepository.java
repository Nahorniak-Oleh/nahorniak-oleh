package org.epam.nahorniak.spring.homework_3.repository;

import org.epam.nahorniak.spring.homework_3.model.Request;

import java.util.List;

public interface RequestRepository {

    Request createRequest(String email,int tariffId);
    Request closeRequest(String email);
    Request activateRequest(String email);
    Request suspendRequest(String email);
    List<Request> getAllByUserEmail(String email);
    Request getActiveOrSuspendedRequestByUserEmail(String email);
}
