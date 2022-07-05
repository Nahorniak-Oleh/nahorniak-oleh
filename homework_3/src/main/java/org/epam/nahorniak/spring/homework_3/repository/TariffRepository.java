package org.epam.nahorniak.spring.homework_3.repository;

import org.epam.nahorniak.spring.homework_3.model.Tariff;

import java.util.List;

public interface TariffRepository {

    List<Tariff> listTariffs();

    Tariff getTariff(int id);

    Tariff createTariff(Tariff tariff);

    Tariff updateTariff(int id,Tariff tariff);

    void deleteTariff(int id);
}
