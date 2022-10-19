package org.epam.nahorniak.spring.internetserviceprovider.mapper;

import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.TariffDto;
import org.epam.nahorniak.spring.internetserviceprovider.model.Tariff;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TariffMapper {

    TariffMapper INSTANCE = Mappers.getMapper(TariffMapper.class);

    List<TariffDto> mapListOfTariffsToListOfDto(List<Tariff> tariffs);

    TariffDto mapTariffToTariffDto(Tariff tariff);

    Tariff mapTariffDtoToTariff(TariffDto tariffDto);
}
