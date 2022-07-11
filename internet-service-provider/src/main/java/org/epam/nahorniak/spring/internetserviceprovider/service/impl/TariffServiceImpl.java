package org.epam.nahorniak.spring.internetserviceprovider.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.TariffDto;
import org.epam.nahorniak.spring.internetserviceprovider.mapper.TariffMapper;
import org.epam.nahorniak.spring.internetserviceprovider.model.Tariff;
import org.epam.nahorniak.spring.internetserviceprovider.repository.TariffRepository;
import org.epam.nahorniak.spring.internetserviceprovider.service.ServicesService;
import org.epam.nahorniak.spring.internetserviceprovider.service.TariffService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TariffServiceImpl implements TariffService {

    private final ServicesService servicesService;
    private final TariffRepository tariffRepository;

    @Override
    public List<TariffDto> listTariffs() {
        log.info("TariffService --> get all tariffs");

        List<Tariff> tariffs = tariffRepository.listTariffs();
        List<TariffDto> tariffDtoList = TariffMapper.INSTANCE.mapListOfTariffsToListOfDto(tariffs);
        tariffDtoList.forEach(item -> item.setServices(servicesService.getAllByTariffId(item.getId())));

        return tariffDtoList;
    }

    @Override
    public TariffDto getTariff(int id) {
        log.info("TariffService --> get tariff by id {}", id);
        Tariff tariff = tariffRepository.getTariff(id);
        TariffDto tariffDto = TariffMapper.INSTANCE.mapTariffToTariffDto(tariff);
        tariffDto.setServices(servicesService.getAllByTariffId(tariffDto.getId()));

        return tariffDto;
    }

    @Override
    public TariffDto createTariff(TariffDto tariffDto) {
        log.info("TariffService --> create tariff with body {}", tariffDto);

        Tariff tariff = TariffMapper.INSTANCE.mapTariffDtoToTariff(tariffDto);
        tariff = tariffRepository.createTariff(tariff);
        return TariffMapper.INSTANCE.mapTariffToTariffDto(tariff);
    }

    @Override
    public TariffDto updateTariff(int id, TariffDto tariffDto) {
        log.info("TariffService --> update tariff by id ({}) with body {}", id, tariffDto);

        Tariff tariff = TariffMapper.INSTANCE.mapTariffDtoToTariff(tariffDto);
        tariff = tariffRepository.updateTariff(id, tariff);
        return TariffMapper.INSTANCE.mapTariffToTariffDto(tariff);
    }

    @Override
    public void deleteTariff(int id) {
        log.info("TariffService --> delete tariff by id ({})", id);
        tariffRepository.deleteTariff(id);
    }
}
