package org.epam.nahorniak.spring.internetserviceprovider.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.ServiceDto;
import org.epam.nahorniak.spring.internetserviceprovider.exception.ServiceNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.exception.TariffNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.mapper.ServicesMapper;
import org.epam.nahorniak.spring.internetserviceprovider.model.ServiceModel;
import org.epam.nahorniak.spring.internetserviceprovider.model.Tariff;
import org.epam.nahorniak.spring.internetserviceprovider.repository.ServiceRepository;
import org.epam.nahorniak.spring.internetserviceprovider.repository.TariffRepository;
import org.epam.nahorniak.spring.internetserviceprovider.service.ServicesService;
import org.epam.nahorniak.spring.internetserviceprovider.service.update.UpdateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
public class ServicesServiceImpl implements ServicesService {

    private final ServiceRepository serviceRepository;
    private final TariffRepository tariffRepository;

    private final UpdateService<ServiceModel,ServiceDto> updateService;

    @Override
    public List<ServiceDto> listServices() {
        log.info("ServicesService --> get all services");
        List<ServiceModel> servicesList = serviceRepository.findAll();
        List<ServiceDto> services = ServicesMapper.INSTANCE.mapListOfServicesToListOfDto(servicesList);
        return services;
    }

    @Override
    public ServiceDto getService(Long id) {
        log.info("ServicesService --> get service by id {}", id);
        ServiceModel service= serviceRepository.findServiceById(id).orElseThrow(ServiceNotFoundException::new);
        return ServicesMapper.INSTANCE.mapServiceToServiceDto(service);
    }

    @Override
    public ServiceDto createService(ServiceDto serviceDto) {
        log.info("ServicesService --> create service with body {}", serviceDto);
        ServiceModel service =
                ServicesMapper.INSTANCE.mapServiceDtoToService(serviceDto);
        service = serviceRepository.save(service);
        return ServicesMapper.INSTANCE.mapServiceToServiceDto(service);
    }

    @Override
    public ServiceDto updateService(Long id, ServiceDto serviceDto) {
        log.info("ServicesService --> update service by id ({}) with body {}", id, serviceDto);
        ServiceModel persistedService = serviceRepository.findServiceById(id).orElseThrow(ServiceNotFoundException::new);
        ServiceModel storedService = serviceRepository.save(persistedService);
        return ServicesMapper.INSTANCE.mapServiceToServiceDto(persistedService);
    }

    @Override
    public void deleteService(Long id) {
        log.info("ServicesService --> delete service by id ({})", id);
        ServiceModel service=
                serviceRepository.findServiceById(id).orElseThrow(ServiceNotFoundException::new);
        serviceRepository.delete(service);
    }

    @Override
    public Set<ServiceDto> getAllByTariffId(Long tariffId) {
        log.info("ServicesService --> get all services by tariffId ({})", tariffId);
        Tariff tariff = tariffRepository.findTariffById(tariffId).orElseThrow(TariffNotFoundException::new);
        return ServicesMapper.INSTANCE.mapSetOfServicesToSetOfDto(tariff.getServices());
    }
}
