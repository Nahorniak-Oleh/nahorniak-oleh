package org.epam.nahorniak.spring.internetserviceprovider.service;

import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.TariffDto;
import org.epam.nahorniak.spring.internetserviceprovider.model.ServiceModel;

import java.util.List;

public interface TariffService {

    List<TariffDto> listTariffs();

    TariffDto getTariff(Long id);

    TariffDto createTariff(TariffDto tariffDto);

    TariffDto updateTariff(Long id, TariffDto tariffDto);

    TariffDto addService(Long tariffId, Long serviceId);

    TariffDto removeService(Long tariffId, Long serviceId);

    void deleteTariff(Long id);

}
