package org.epam.nahorniak.spring.internetserviceprovider.service;

import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.ServiceDto;

import java.util.List;

public interface ServicesService {

    List<ServiceDto> listServices();

    ServiceDto getService(int id);

    ServiceDto createService(ServiceDto serviceDto);

    ServiceDto updateService(int id, ServiceDto serviceDto);

    void deleteService(int id);

    List<ServiceDto> getAllByTariffId(int tariffId);
}
