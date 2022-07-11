package org.epam.nahorniak.spring.internetserviceprovider.service;

import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.TariffDto;

import java.util.List;

public interface TariffService {

    List<TariffDto> listTariffs();

    TariffDto getTariff(int id);

    TariffDto createTariff(TariffDto tariffDto);

    TariffDto updateTariff(int id, TariffDto tariffDto);

    void deleteTariff(int id);

}
