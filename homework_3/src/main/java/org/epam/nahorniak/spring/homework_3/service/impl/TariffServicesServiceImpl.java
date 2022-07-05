package org.epam.nahorniak.spring.homework_3.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.epam.nahorniak.spring.homework_3.repository.TariffServicesRepository;
import org.epam.nahorniak.spring.homework_3.service.TariffServicesService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TariffServicesServiceImpl implements TariffServicesService {

    private final TariffServicesRepository tariffServicesRepository;

    @Override
    public void addServiceToTariff(int tariffId, int serviceId) {
        log.info("TariffServicesService --> add service to tariff by tariffId ({}) and serviceId ({})",tariffId,serviceId);
        tariffServicesRepository.addServiceToTariff(tariffId,serviceId);
    }

    @Override
    public void deleteServiceFromTariff(int tariffId, int serviceId) {
        log.info("TariffServicesService --> delete service from tariff by tariffId ({}) and serviceId ({})",tariffId,serviceId);
        tariffServicesRepository.deleteServiceFromTariff(tariffId,serviceId);
    }
}
