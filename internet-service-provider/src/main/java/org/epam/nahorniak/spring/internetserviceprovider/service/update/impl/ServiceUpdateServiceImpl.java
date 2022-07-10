package org.epam.nahorniak.spring.internetserviceprovider.service.update.impl;

import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.ServiceDto;
import org.epam.nahorniak.spring.internetserviceprovider.model.ServiceModel;
import org.epam.nahorniak.spring.internetserviceprovider.service.update.UpdateService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ServiceUpdateServiceImpl implements UpdateService<ServiceModel, ServiceDto> {

    @Override
    public ServiceModel updateObject(ServiceModel updatable, ServiceDto dto) {
        System.out.println(dto);
        final String code = dto.getCode();
        if(Objects.nonNull(code)){
            updatable.setCode(code);
        }

        final String title = dto.getTitle();
        if(Objects.nonNull(title)){
            updatable.setTitle(title);
        }

        return updatable;
    }
}
