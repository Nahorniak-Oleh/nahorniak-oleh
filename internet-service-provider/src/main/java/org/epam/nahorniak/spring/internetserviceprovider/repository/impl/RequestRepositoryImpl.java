package org.epam.nahorniak.spring.internetserviceprovider.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.epam.nahorniak.spring.internetserviceprovider.model.Request;
import org.epam.nahorniak.spring.internetserviceprovider.model.Status;
import org.epam.nahorniak.spring.internetserviceprovider.model.User;
import org.epam.nahorniak.spring.internetserviceprovider.repository.RequestRepository;
import org.epam.nahorniak.spring.internetserviceprovider.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RequestRepositoryImpl implements RequestRepository {

    private final UserRepository userRepository;

    List<Request> requests = new ArrayList<>();

    {
        Request request = Request.builder()
                .id(1)
                .userId("1")
                .tariffId(1)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(3))
                .status(Status.SUSPENDED)
                .build();

        requests.add(request);
    }

    @Override
    public Request createRequest(String email, int tariffId) {

        log.info("RequestRepository --> create request with user email ({}) and tariff id - ({})", email, tariffId);
        User user = userRepository.getUser(email);
        if (user == null) throw new RuntimeException("User is not found!");

        LocalDate NOW = LocalDate.now();

        Request request = Request.builder()
                .id(requests.size() + 1)
                .userId(user.getId())
                .tariffId(tariffId)
                .startDate(NOW)
                .endDate(NOW.plusMonths(1))
                .status(Status.ACTIVE)
                .build();


        requests.add(request);
        return request;
    }

    @Override
    public Request closeRequest(String email) {
        log.info("RequestRepository --> close request by user email {}", email);

        Request request = getActiveOrSuspendedRequestByUserEmail(email);
        requests.remove(request);

        request.setEndDate(LocalDate.now());
        request.setStatus(Status.CLOSED);
        requests.add(request);
        return request;
    }

    @Override
    public Request activateRequest(String email) {
        log.info("RequestRepository --> activate request by user email {}", email);

        Request request = getActiveOrSuspendedRequestByUserEmail(email);
        requests.remove(request);

        request.setStatus(Status.ACTIVE);
        requests.add(request);
        return request;
    }

    @Override
    public Request suspendRequest(String email) {
        log.info("RequestRepository --> suspend request by user email {}", email);
        Request request = getActiveOrSuspendedRequestByUserEmail(email);
        requests.remove(request);

        request.setStatus(Status.SUSPENDED);
        requests.add(request);
        return request;
    }

    @Override
    public List<Request> getAllByUserEmail(String email) {
        log.info("RequestService --> get all requests by user email {}", email);

        User user = userRepository.getUser(email);
        if (user == null) throw new RuntimeException("User is not found!");
        return requests.stream()
                .filter(request -> user.getId().equals(request.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public Request getActiveOrSuspendedRequestByUserEmail(String email) {
        log.info("RequestService --> get active or suspended request by user email {}", email);

        User user = userRepository.getUser(email);
        if (user == null) throw new RuntimeException("User is not found!");
        return requests.stream()
                .filter(request -> user.getId().equals(request.getUserId()) &&
                        (request.getStatus().equals(Status.ACTIVE) || request.getStatus().equals(Status.SUSPENDED)))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Request is not found!"));
    }
}
