package org.epam.nahorniak.spring.internetserviceprovider.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.epam.nahorniak.spring.internetserviceprovider.model.TariffServices;
import org.epam.nahorniak.spring.internetserviceprovider.repository.TariffServicesRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class TariffServicesRepositoryImpl implements TariffServicesRepository {

    List<TariffServices> tariffServicesList = new ArrayList<>();

    {
        TariffServices tariffServices =
                TariffServices.builder()
                        .tariffId(1)
                        .serviceId(1)
                        .build();

        tariffServicesList.add(tariffServices);
    }

    @Override
    public List<Integer> getServicesByTariffId(int tariffId) {
        log.info("TariffServicesRepository --> get all services by tariffId ({})", tariffId);
        return tariffServicesList.stream()
                .filter(item -> item.getTariffId() == tariffId)
                .map(item -> item.getServiceId()).collect(Collectors.toList());
    }

    public void deleteByTariffId(int tariffId) {
        log.info("TariffServicesRepository --> delete by tariffId ({})", tariffId);
        tariffServicesList.removeIf(item -> item.getTariffId() == tariffId);
    }

    @Override
    public void deleteByServiceId(int serviceId) {
        log.info("TariffServicesRepository --> delete by serviceId ({})", serviceId);
        tariffServicesList.removeIf(item -> item.getServiceId() == serviceId);
    }

    @Override
    public void addServiceToTariff(int tariffId, int serviceId) {
        log.info("TariffServicesRepository --> add service to tariff by tariffId ({}) and serviceId ({})", tariffId, serviceId);
        TariffServices tariffServices = TariffServices.builder().tariffId(tariffId).serviceId(serviceId).build();
        tariffServicesList.add(tariffServices);
    }

    @Override
    public void deleteServiceFromTariff(int tariffId, int serviceId) {
        log.info("TariffServicesRepository --> delete service from tariff by tariffId ({}) and serviceId ({})", tariffId, serviceId);
        tariffServicesList.removeIf(item -> item.getServiceId() == serviceId && item.getTariffId() == tariffId);
    }
}
