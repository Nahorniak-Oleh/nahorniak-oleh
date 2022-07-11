package org.epam.nahorniak.spring.internetserviceprovider.mapper;

import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.ServiceDto;
import org.epam.nahorniak.spring.internetserviceprovider.model.ServiceModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServicesMapper {

    ServicesMapper INSTANCE = Mappers.getMapper(ServicesMapper.class);

    List<ServiceDto> mapListOfServicesToListOfDto(List<ServiceModel> services);

    Set<ServiceDto> mapSetOfServicesToSetOfDto(Set<ServiceModel> services);

    ServiceDto mapServiceToServiceDto(ServiceModel service);

    ServiceModel mapServiceDtoToService(ServiceDto serviceDto);
}
