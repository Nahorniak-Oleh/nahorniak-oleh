package org.epam.nahorniak.spring.homework_3.service;

import org.epam.nahorniak.spring.homework_3.controller.dto.TariffDto;

import java.util.List;

public interface TariffService {

    List<TariffDto> listTariffs();

    TariffDto getTariff(int id);

    TariffDto createTariff(TariffDto tariffDto);

    TariffDto updateTariff(int id, TariffDto tariffDto);

    void deleteTariff(int id);

}
