package org.epam.nahorniak.spring.internetserviceprovider.repository;

import org.epam.nahorniak.spring.internetserviceprovider.model.ServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<ServiceModel, Long> {
    @Query("SELECT s FROM ServiceModel s WHERE s.id = ?1")
    Optional<ServiceModel> findServiceById(Long id);
}
