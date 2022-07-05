package org.epam.nahorniak.spring.homework_3.mapper;

import org.epam.nahorniak.spring.homework_3.controller.dto.ServiceDto;
import org.epam.nahorniak.spring.homework_3.model.Service;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServicesMapper {

    ServicesMapper INSTANCE = Mappers.getMapper(ServicesMapper.class);

    List<ServiceDto> mapListOfServicesToListOfDto(List<Service> services);

    ServiceDto mapServiceToServiceDto(Service service);

    Service mapServiceDtoToService(ServiceDto serviceDto);
}
