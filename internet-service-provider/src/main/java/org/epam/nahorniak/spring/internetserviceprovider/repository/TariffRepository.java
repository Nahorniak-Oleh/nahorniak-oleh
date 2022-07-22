package org.epam.nahorniak.spring.internetserviceprovider.repository;

import org.epam.nahorniak.spring.internetserviceprovider.model.Tariff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TariffRepository extends JpaRepository<Tariff, Long> {
    @Query("SELECT t FROM Tariff t WHERE t.id = ?1")
    Optional<Tariff> findTariffById(long id);
}
