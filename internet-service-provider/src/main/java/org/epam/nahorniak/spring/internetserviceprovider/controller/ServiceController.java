package org.epam.nahorniak.spring.internetserviceprovider.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.ServiceDto;
import org.epam.nahorniak.spring.internetserviceprovider.service.ServicesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ServiceController {

    private final ServicesService servicesService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/service")
    public List<ServiceDto> getAllServices() {
        log.info("get all services");
        return servicesService.listServices();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/service/{id}")
    public ServiceDto getServiceById(@PathVariable int id) {
        log.info("get service by id {}", id);
        return servicesService.getService(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/service")
    public ServiceDto createService(@RequestBody ServiceDto serviceDto) {
        log.info("create service with body {}", serviceDto);
        return servicesService.createService(serviceDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/service/{id}")
    public ServiceDto updateService(@PathVariable int id, @RequestBody ServiceDto serviceDto) {
        log.info("update service by id ({}) with body {}", id, serviceDto);
        return servicesService.updateService(id, serviceDto);
    }

    @DeleteMapping(value = "/service/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable int id) {
        log.info("delete service by id ({})", id);
        servicesService.deleteService(id);
        return ResponseEntity.noContent().build();
    }

}
