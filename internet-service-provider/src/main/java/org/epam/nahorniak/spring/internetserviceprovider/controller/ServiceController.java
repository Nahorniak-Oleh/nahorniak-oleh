package org.epam.nahorniak.spring.internetserviceprovider.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.epam.nahorniak.spring.internetserviceprovider.api.ServiceApi;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.ServiceDto;
import org.epam.nahorniak.spring.internetserviceprovider.service.ServicesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ServiceController implements ServiceApi {

    private final ServicesService servicesService;

    @Override
    public List<ServiceDto> getAllTariffs() {
        log.info("get all services");
        return servicesService.listServices();
    }

    @Override
    public ServiceDto getTariffById(int id) {
        log.info("get service by id {}", id);
        return servicesService.getService(id);
    }

    @Override
    public ServiceDto createService(ServiceDto serviceDto) {
        log.info("create service with body {}", serviceDto);
        return servicesService.createService(serviceDto);
    }

    @Override
    public ServiceDto updateService(int id,ServiceDto serviceDto) {
        log.info("update service by id ({}) with body {}", id, serviceDto);
        return servicesService.updateService(id, serviceDto);
    }

    @Override
    public ResponseEntity<Void> deleteService(int id) {
        log.info("delete service by id ({})", id);
        servicesService.deleteService(id);
        return ResponseEntity.noContent().build();
    }

}
