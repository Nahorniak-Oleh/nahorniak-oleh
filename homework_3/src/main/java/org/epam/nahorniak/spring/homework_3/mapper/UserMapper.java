package org.epam.nahorniak.spring.homework_3.mapper;


import org.epam.nahorniak.spring.homework_3.controller.dto.UserDto;
import org.epam.nahorniak.spring.homework_3.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    List<UserDto> mapListOfUserToListOfDto(List<User> users);

    @Mapping(target = "password", ignore = true)
    UserDto mapUserToUserDto(User user);

    User mapUserDtoToUser(UserDto userDto);

}
