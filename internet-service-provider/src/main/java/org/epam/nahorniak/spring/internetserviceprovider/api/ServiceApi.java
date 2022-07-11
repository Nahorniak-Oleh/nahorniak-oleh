package org.epam.nahorniak.spring.internetserviceprovider.api;

import io.swagger.annotations.*;
import org.epam.nahorniak.spring.internetserviceprovider.controller.dto.ServiceDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Service management API")
@ApiResponses({
        @ApiResponse(code = 400, message = "Validation Error"),
        @ApiResponse(code = 404, message = "Not found"),
        @ApiResponse(code = 500, message = "Internal Server Error")
})
@RequestMapping("/api/v1/services")
public interface ServiceApi {

    @ApiOperation("Get all services")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    List<ServiceDto> getAllServices();

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Service id")
    })
    @ApiOperation("Get service by id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}")
    ServiceDto getServiceById(@PathVariable int id);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Service id")
    })
    @ApiOperation("Create service")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    ServiceDto createService(@RequestBody @Valid ServiceDto serviceDto);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", paramType = "path", required = true, value = "Service id")
    })
    @ApiOperation("Update service")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{id}")
    ServiceDto updateService(@PathVariable int id, @RequestBody @Valid ServiceDto serviceDto);

    @ApiOperation("Delete service")
    @DeleteMapping(value = "/{id}")
    ResponseEntity<Void> deleteService(@PathVariable int id);
}
