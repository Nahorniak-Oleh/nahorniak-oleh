package org.epam.nahorniak.spring.internetserviceprovider.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.epam.nahorniak.spring.internetserviceprovider.model.Service;
import org.epam.nahorniak.spring.internetserviceprovider.repository.ServicesRepository;
import org.epam.nahorniak.spring.internetserviceprovider.repository.TariffServicesRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Slf4j
public class ServicesRepositoryImpl implements ServicesRepository {

    private final TariffServicesRepository tariffServicesRepository;

    List<Service> services = new ArrayList<>();

    {
        Service service =
                Service.builder()
                        .id(1)
                        .title("IP-TV")
                        .code("#IPTV")
                        .build();

        services.add(service);
    }

    @Override
    public List<Service> listServices() {
        log.info("ServiceRepository --> get all services");
        return new ArrayList<>(services);
    }

    @Override
    public Service getService(int id) {
        log.info("ServiceRepository --> get service by id {}", id);
        return services.stream()
                .filter(service -> service.getId() == id)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Service is not found!"));
    }

    @Override
    public Service createService(Service service) {
        service.setId(services.size() + 1);
        log.info("ServiceRepository --> create service with body {}", service);
        services.add(service);
        return service;
    }

    @Override
    public Service updateService(int id, Service service) {
        log.info("ServiceRepository --> update service by id ({}) with body {}", id, service);

        boolean isDeleted = services.removeIf(u -> u.getId() == id);
        if (isDeleted) {
            service.setId(id);
            services.add(service);
        } else {
            throw new RuntimeException("Service is not found!");
        }
        return service;
    }

    @Override
    public void deleteService(int id) {
        log.info("ServiceRepository --> delete service by id ({})", id);
        services.removeIf(u -> u.getId() == id);
        tariffServicesRepository.deleteByServiceId(id);
    }

    @Override
    public List<Service> getAllByTariffId(int tariffId) {
        log.info("ServiceRepository --> get all services by tariffId ({})", tariffId);
        List<Integer> servicesByTariff = tariffServicesRepository.getServicesByTariffId(tariffId);
        List<Service> servicesList = new ArrayList<>();
        servicesByTariff.forEach(item -> servicesList.add(services.get(item - 1)));
        return servicesList;
    }
}
