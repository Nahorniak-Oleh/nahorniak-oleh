package org.epam.nahorniak.spring.homework_3.mapper;

import org.epam.nahorniak.spring.homework_3.controller.dto.RequestDto;
import org.epam.nahorniak.spring.homework_3.controller.dto.TariffDto;
import org.epam.nahorniak.spring.homework_3.model.Request;
import org.epam.nahorniak.spring.homework_3.model.Tariff;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RequestMapper {

    RequestMapper INSTANCE = Mappers.getMapper(RequestMapper.class);

    List<RequestDto> mapListOfRequestsToListOfDto(List<Request> requests);

    RequestDto mapRequestToRequestDto(Request request);

    Request mapRequestDtoToRequest(RequestDto requestDto);
}
