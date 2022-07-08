package org.epam.nahorniak.spring.internetserviceprovider.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.epam.nahorniak.spring.internetserviceprovider.api.TariffApi;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.TariffDto;
import org.epam.nahorniak.spring.internetserviceprovider.service.TariffService;
import org.epam.nahorniak.spring.internetserviceprovider.service.TariffServicesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TariffController implements TariffApi {

    private final TariffServicesService tariffServicesService;
    private final TariffService tariffService;

    @Override
    public List<TariffDto> getAllTariffs() {
        log.info("get all tariffs");
        return tariffService.listTariffs();
    }

    @Override
    public TariffDto getTariffById(int id) {
        log.info("get tariff by id {}", id);
        return tariffService.getTariff(id);
    }

    @Override
    public TariffDto createTariff(TariffDto tariffDto) {
        log.info("create tariff with body {}", tariffDto);
        return tariffService.createTariff(tariffDto);
    }

    @Override
    public TariffDto updateTariff(int id,TariffDto tariffDto) {
        log.info("update tariff by id ({}) with body {}", id, tariffDto);
        return tariffService.updateTariff(id, tariffDto);
    }

    @Override
    public ResponseEntity<Void> deleteTariff(int id) {
        log.info("delete tariff by id ({})", id);
        tariffService.deleteTariff(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public TariffDto addServiceToTariff(int tariffId,int serviceId) {
        log.info("add service to tariff by tariffId ({}) and serviceId ({})", tariffId, serviceId);
        tariffServicesService.addServiceToTariff(tariffId, serviceId);
        return tariffService.getTariff(tariffId);
    }

    @Override
    public TariffDto deleteServiceFromTariff(int tariffId,int serviceId) {
        log.info("delete service from tariff by tariffId ({}) and serviceId ({})", tariffId, serviceId);
        tariffServicesService.deleteServiceFromTariff(tariffId, serviceId);
        return tariffService.getTariff(tariffId);
    }
}
