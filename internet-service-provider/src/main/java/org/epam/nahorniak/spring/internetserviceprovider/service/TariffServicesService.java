package org.epam.nahorniak.spring.internetserviceprovider.service;

public interface TariffServicesService {

    void addServiceToTariff(int tariffId, int serviceId);

    void deleteServiceFromTariff(int tariffId, int serviceId);
}
