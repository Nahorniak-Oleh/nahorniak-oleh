package org.epam.nahorniak.spring.internetserviceprovider.api;

import io.swagger.annotations.*;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.TariffDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Tariff management API")
@ApiResponses({
        @ApiResponse(code = 400, message = "Validation Error"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@RequestMapping("/api/v1/tariffs")
public interface TariffApi {

    @ApiOperation("Get all tariffs")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    List<TariffDto> getAllTariffs();

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Tariff id")
    })
    @ApiOperation("Get tariff by id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    TariffDto getTariffById(@PathVariable int id);

    @ApiOperation("Create tariff")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    TariffDto createTariff(@RequestBody @Valid TariffDto tariffDto);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Tariff id")
    })
    @ApiOperation("Update tariff")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}")
    TariffDto updateTariff(@PathVariable int id, @RequestBody @Valid TariffDto tariffDto);

    @ApiOperation("Delete tariff")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> deleteTariff(@PathVariable int id);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "tariffId", paramType = "path", required = true, value = "Tariff id"),
            @ApiImplicitParam(name = "serviceId", paramType = "path", required = true, value = "Service id")
    })
    @ApiOperation("Add service to tariff")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{tariffId}/{serviceId}")
    TariffDto addServiceToTariff(@PathVariable int tariffId, @PathVariable int serviceId);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "tariffId", paramType = "path", required = true, value = "Tariff id"),
            @ApiImplicitParam(name = "serviceId", paramType = "path", required = true, value = "Service id")
    })
    @ApiOperation("Add service to tariff")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{tariffId}/{serviceId}")
    TariffDto deleteServiceFromTariff(@PathVariable int tariffId, @PathVariable int serviceId);

}
