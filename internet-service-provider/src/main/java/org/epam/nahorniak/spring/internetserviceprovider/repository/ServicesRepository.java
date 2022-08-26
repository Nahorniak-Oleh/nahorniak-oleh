package org.epam.nahorniak.spring.internetserviceprovider.repository;


import org.epam.nahorniak.spring.internetserviceprovider.model.Service;

import java.util.List;

public interface ServicesRepository {

    List<Service> listServices();

    Service getService(int id);

    Service createService(Service service);

    Service updateService(int id, Service service);

    void deleteService(int id);

    List<Service> getAllByTariffId(int tariffId);
}
