package org.epam.nahorniak.spring.internetserviceprovider.repository.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.epam.nahorniak.spring.internetserviceprovider.exceptions.EntityNotFoundException;
import org.epam.nahorniak.spring.internetserviceprovider.model.Tariff;
import org.epam.nahorniak.spring.internetserviceprovider.repository.TariffRepository;
import org.epam.nahorniak.spring.internetserviceprovider.repository.TariffServicesRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
@Slf4j
public class TariffRepositoryImpl implements TariffRepository {

    private static final String TARIFF_IS_NOT_FOUND_MESSAGE = "Tariff is not found!";
    private final TariffServicesRepository tariffServicesRepository;

    private final List<Tariff> tariffs = new ArrayList<>();

    {
        Tariff tariff =
                Tariff.builder()
                        .id(1)
                        .title("Standard")
                        .code("#Standard")
                        .price(5.5)
                        .build();

        tariffs.add(tariff);
    }

    @Override
    public List<Tariff> listTariffs() {
        log.info("TariffRepository --> get all tariffs");
        return new ArrayList<>(tariffs);
    }

    @Override
    public Tariff getTariff(int id) {
        log.info("TariffRepository --> get tariff by id {}", id);
        return tariffs.stream()
                .filter(tariff -> tariff.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(TARIFF_IS_NOT_FOUND_MESSAGE));
    }

    @Override
    public Tariff createTariff(Tariff tariff) {
        tariff.setId(tariffs.size() + 1);
        log.info("TariffRepository --> create tariff with body {}", tariff);

        tariffs.add(tariff);
        return tariff;
    }

    @Override
    public Tariff updateTariff(int id, Tariff tariff) {
        log.info("TariffRepository --> update tariff by id ({}) with body {}", id, tariff);

        boolean isDeleted = tariffs.removeIf(u -> u.getId() == id);
        if (isDeleted) {
            tariff.setId(id);
            tariffs.add(tariff);
        } else {
            throw new EntityNotFoundException(TARIFF_IS_NOT_FOUND_MESSAGE);
        }
        return tariff;
    }

    @Override
    public void deleteTariff(int id) {
        log.info("TariffRepository --> delete tariff by id ({})", id);
        tariffs.removeIf(u -> u.getId() == id);
        tariffServicesRepository.deleteByTariffId(id);
    }
}
