package org.epam.nahorniak.spring.internetserviceprovider.repository;

import java.util.List;

public interface TariffServicesRepository {

    List<Integer> getServicesByTariffId(int tariffId);

    void deleteByTariffId(int tariffId);

    void deleteByServiceId(int serviceId);

    void addServiceToTariff(int tariffId, int serviceId);

    void deleteServiceFromTariff(int tariffId, int serviceId);
}
