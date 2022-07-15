package org.epam.nahorniak.spring.internetserviceprovider.service.update;

public interface UpdateService <T,K> {
    T updateObject(T updatable, K dto);
}
