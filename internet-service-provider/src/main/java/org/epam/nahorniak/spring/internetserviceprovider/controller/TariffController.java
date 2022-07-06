package org.epam.nahorniak.spring.internetserviceprovider.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.TariffDto;
import org.epam.nahorniak.spring.internetserviceprovider.service.TariffService;
import org.epam.nahorniak.spring.internetserviceprovider.service.TariffServicesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TariffController {

    private final TariffServicesService tariffServicesService;
    private final TariffService tariffService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tariff")
    public List<TariffDto> getAllTariffs() {
        log.info("get all tariffs");
        return tariffService.listTariffs();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/tariff/{id}")
    public TariffDto getTariffById(@PathVariable int id) {
        log.info("get tariff by id {}", id);
        return tariffService.getTariff(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/tariff")
    public TariffDto createTariff(@RequestBody TariffDto tariffDto) {
        log.info("create tariff with body {}", tariffDto);
        return tariffService.createTariff(tariffDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/tariff/{id}")
    public TariffDto updateTariff(@PathVariable int id, @RequestBody TariffDto tariffDto) {
        log.info("update tariff by id ({}) with body {}", id, tariffDto);
        return tariffService.updateTariff(id, tariffDto);
    }

    @DeleteMapping(value = "/tariff/{id}")
    public ResponseEntity<Void> deleteTariff(@PathVariable int id) {
        log.info("delete tariff by id ({})", id);
        tariffService.deleteTariff(id);
        return ResponseEntity.noContent().build();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(value = "/tariff/{tariffId}/{serviceId}")
    public TariffDto addServiceToTariff(@PathVariable int tariffId, @PathVariable int serviceId) {
        log.info("add service to tariff by tariffId ({}) and serviceId ({})", tariffId, serviceId);
        tariffServicesService.addServiceToTariff(tariffId, serviceId);
        return tariffService.getTariff(tariffId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @DeleteMapping(value = "/tariff/{tariffId}/{serviceId}")
    public TariffDto deleteServiceFromTariff(@PathVariable int tariffId, @PathVariable int serviceId) {
        log.info("delete service from tariff by tariffId ({}) and serviceId ({})", tariffId, serviceId);
        tariffServicesService.deleteServiceFromTariff(tariffId, serviceId);
        return tariffService.getTariff(tariffId);
    }


}
