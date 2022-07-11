package org.epam.nahorniak.spring.internetserviceprovider.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.epam.nahorniak.spring.internetserviceprovider.api.TariffApi;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.TariffDto;
import org.epam.nahorniak.spring.internetserviceprovider.service.TariffService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TariffController implements TariffApi {

    private final TariffService tariffService;

    @Override
    public List<TariffDto> getAllTariffs(int page,int size) {
        log.info("get all tariffs");
        return tariffService.listTariffs(page,size);
    }

    @Override
    public TariffDto getTariffById(Long id) {
        log.info("get tariff by id {}", id);
        return tariffService.getTariff(id);
    }

    @Override
    public TariffDto createTariff(TariffDto tariffDto) {
        log.info("create tariff with body {}", tariffDto);
        return tariffService.createTariff(tariffDto);
    }

    @Override
    public TariffDto updateTariff(Long id,TariffDto tariffDto) {
        log.info("update tariff by id ({}) with body {}", id, tariffDto);
        return tariffService.updateTariff(id, tariffDto);
    }

    @Override
    public ResponseEntity<Void> deleteTariff(Long id) {
        log.info("delete tariff by id ({})", id);
        tariffService.deleteTariff(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public TariffDto addServiceToTariff(Long tariffId,Long serviceId) {
        log.info("add service to tariff by tariffId ({}) and serviceId ({})", tariffId, serviceId);
        return tariffService.addService(tariffId, serviceId);
    }

    @Override
    public TariffDto deleteServiceFromTariff(Long tariffId,Long serviceId) {
        log.info("delete service from tariff by tariffId ({}) and serviceId ({})", tariffId, serviceId);
        return tariffService.removeService(tariffId,serviceId);
    }
}
