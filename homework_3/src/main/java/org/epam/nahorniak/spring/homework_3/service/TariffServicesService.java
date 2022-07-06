package org.epam.nahorniak.spring.homework_3.service;

public interface TariffServicesService {

    void addServiceToTariff(int tariffId, int serviceId);

    void deleteServiceFromTariff(int tariffId, int serviceId);
}
