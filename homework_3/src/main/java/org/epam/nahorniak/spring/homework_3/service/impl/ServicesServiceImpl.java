package org.epam.nahorniak.spring.homework_3.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.epam.nahorniak.spring.homework_3.controller.dto.ServiceDto;
import org.epam.nahorniak.spring.homework_3.mapper.ServicesMapper;
import org.epam.nahorniak.spring.homework_3.repository.ServicesRepository;
import org.epam.nahorniak.spring.homework_3.service.ServicesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicesServiceImpl implements ServicesService {

    private final ServicesRepository servicesRepository;

    @Override
    public List<ServiceDto> listServices() {
        log.info("ServicesService --> get all services");
        List<org.epam.nahorniak.spring.homework_3.model.Service> servicesList = servicesRepository.listServices();
        List<ServiceDto> services = ServicesMapper.INSTANCE.mapListOfServicesToListOfDto(servicesList);
        return services;
    }

    @Override
    public ServiceDto getService(int id) {
        log.info("ServicesService --> get service by id {}",id);
        return ServicesMapper
                .INSTANCE
                .mapServiceToServiceDto(servicesRepository.getService(id));
    }

    @Override
    public ServiceDto createService(ServiceDto serviceDto) {
        log.info("ServicesService --> create service with body {}",serviceDto);
        org.epam.nahorniak.spring.homework_3.model.Service service =
                ServicesMapper.INSTANCE.mapServiceDtoToService(serviceDto);
        service = servicesRepository.createService(service);
        return ServicesMapper.INSTANCE.mapServiceToServiceDto(service);
    }

    @Override
    public ServiceDto updateService(int id, ServiceDto serviceDto) {
        log.info("ServicesService --> update service by id ({}) with body {}",id,serviceDto);
        org.epam.nahorniak.spring.homework_3.model.Service service =
                ServicesMapper.INSTANCE.mapServiceDtoToService(serviceDto);
        service = servicesRepository.updateService(id,service);
        return ServicesMapper.INSTANCE.mapServiceToServiceDto(service);
    }

    @Override
    public void deleteService(int id) {
        log.info("ServicesService --> delete service by id ({})",id);
        servicesRepository.deleteService(id);
    }

    @Override
    public List<ServiceDto> getAllByTariffId(int tariffId) {
        log.info("ServicesService --> get all services by tariffId ({})",tariffId);
        List<org.epam.nahorniak.spring.homework_3.model.Service> services = servicesRepository.getAllByTariffId(tariffId);
        return ServicesMapper.INSTANCE.mapListOfServicesToListOfDto(services);
    }
}
