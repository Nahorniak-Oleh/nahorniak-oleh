package org.epam.nahorniak.spring.internetserviceprovider.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.TariffDto;
import org.epam.nahorniak.spring.internetserviceprovider.exception.ServiceNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.exception.TariffNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.mapper.TariffMapper;
import org.epam.nahorniak.spring.internetserviceprovider.model.ServiceModel;
import org.epam.nahorniak.spring.internetserviceprovider.model.Tariff;
import org.epam.nahorniak.spring.internetserviceprovider.repository.ServiceRepository;
import org.epam.nahorniak.spring.internetserviceprovider.repository.TariffRepository;
import org.epam.nahorniak.spring.internetserviceprovider.service.TariffService;
import org.epam.nahorniak.spring.internetserviceprovider.service.update.UpdateService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TariffServiceImpl implements TariffService {

    private final TariffRepository tariffRepository;
    private final ServiceRepository serviceRepository;
    private final UpdateService<Tariff, TariffDto> updateService;

    @Override
    public List<TariffDto> listTariffs() {
        log.info("TariffService --> get all tariffs");
        List<Tariff> tariffs = tariffRepository.findAll();
        return TariffMapper.INSTANCE.mapListOfTariffsToListOfDto(tariffs);
    }

    @Override
    public TariffDto getTariff(Long id) {
        log.info("TariffService --> get tariff by id {}", id);
        Tariff tariff = tariffRepository.findTariffById(id)
                .orElseThrow(TariffNotFoundException::new);
        return TariffMapper.INSTANCE.mapTariffToTariffDto(tariff);
    }

    @Override
    public TariffDto createTariff(TariffDto tariffDto) {
        log.info("TariffService --> create tariff with body {}", tariffDto);

        Tariff tariff = TariffMapper.INSTANCE.mapTariffDtoToTariff(tariffDto);
        tariff = tariffRepository.save(tariff);
        return TariffMapper.INSTANCE.mapTariffToTariffDto(tariff);
    }

    @Override
    public TariffDto updateTariff(Long id, TariffDto tariffDto) {
        log.info("TariffService --> update tariff by id ({}) with body {}", id, tariffDto);
        Tariff persistedTariff = tariffRepository.findTariffById(id)
                .orElseThrow(TariffNotFoundException::new);
        persistedTariff = updateService.updateObject(persistedTariff,tariffDto);
        Tariff storedTariff = tariffRepository.save(persistedTariff);
        log.info("TariffService --> Tariff with id {} successfully updated", storedTariff.getId());
        return TariffMapper.INSTANCE.mapTariffToTariffDto(persistedTariff);
    }

    @Override
    public TariffDto addService(Long tariffId, Long serviceId) {
        Tariff tariff = tariffRepository.findTariffById(tariffId)
                .orElseThrow(TariffNotFoundException::new);
        ServiceModel serviceModel = serviceRepository.findServiceById(serviceId)
                .orElseThrow(ServiceNotFoundException::new);

        tariff.addService(serviceModel);
        Tariff storedTariff = tariffRepository.save(tariff);
        log.info("TariffService --> Tariff with id {} successfully updated with adding service", storedTariff.getId());
        return TariffMapper.INSTANCE.mapTariffToTariffDto(tariff);
    }

    @Override
    public TariffDto removeService(Long tariffId, Long serviceId) {
        Tariff tariff = tariffRepository.findTariffById(tariffId)
                .orElseThrow(TariffNotFoundException::new);
        ServiceModel serviceModel = serviceRepository.findServiceById(serviceId)
                .orElseThrow(ServiceNotFoundException::new);

        tariff.removeService(serviceModel);
        Tariff storedTariff = tariffRepository.save(tariff);
        log.info("TariffService --> Tariff with id {} successfully updated with removing service", storedTariff.getId());
        return TariffMapper.INSTANCE.mapTariffToTariffDto(tariff);
    }

    @Override
    public void deleteTariff(Long id) {
        log.info("TariffService --> delete tariff by id ({})", id);
        Tariff tariff = tariffRepository.findTariffById(id)
                .orElseThrow(TariffNotFoundException::new);
        tariffRepository.delete(tariff);
    }
}
