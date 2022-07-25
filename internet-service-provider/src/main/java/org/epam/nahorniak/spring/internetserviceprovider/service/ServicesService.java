package org.epam.nahorniak.spring.internetserviceprovider.service;

import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.ServiceDto;

import java.util.List;
import java.util.Set;

public interface ServicesService {

    List<ServiceDto> listServices(int page,int size);

    ServiceDto getService(Long id);

    ServiceDto createService(ServiceDto serviceDto);

    ServiceDto updateService(Long id, ServiceDto serviceDto);

    void deleteService(Long id);

    Set<ServiceDto> getAllByTariffId(Long tariffId);
}
