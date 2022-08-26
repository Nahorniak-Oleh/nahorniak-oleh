package org.epam.nahorniak.spring.internetserviceprovider.service.update.impl;

import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.TariffDto;
import org.epam.nahorniak.spring.internetserviceprovider.model.Tariff;
import org.epam.nahorniak.spring.internetserviceprovider.service.update.UpdateService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TariffUpdateServiceImpl implements UpdateService<Tariff, TariffDto> {
    @Override
    public Tariff updateObject(Tariff updatable, TariffDto dto) {

        final String code = dto.getCode();
        if(Objects.nonNull(code)){
            updatable.setCode(code);
        }

        final String title = dto.getTitle();
        if(Objects.nonNull(title)){
            updatable.setTitle(title);
        }

        final double price = dto.getPrice();
        if(Objects.nonNull(price)){
            updatable.setPrice(price);
        }

        return updatable;
    }
}
