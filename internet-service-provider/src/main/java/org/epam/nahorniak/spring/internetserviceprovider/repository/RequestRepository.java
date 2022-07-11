package org.epam.nahorniak.spring.internetserviceprovider.repository;

import org.epam.nahorniak.spring.internetserviceprovider.model.Request;
import org.epam.nahorniak.spring.internetserviceprovider.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request,Integer> {
    @Query("SELECT r FROM Request r WHERE r.id = ?1")
    Optional<Request> getRequestById(Long id);

    @Query("SELECT r FROM Request r WHERE r.user = ?1 and r.status = 'ACTIVE' or r.status = 'SUSPENDED'")
    Optional<Request> getRequestByUserAndStatus(User user);

    List<Request> findAllByUser(User user, Pageable pageable);
}
