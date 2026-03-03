package com.dmytro.language_learning_api.mapper;

import com.dmytro.language_learning_api.dto.UsersDTO;
import com.dmytro.language_learning_api.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsersMapper {

    UsersDTO toDto(Users users);
    Users fromDto(UsersDTO usersDTO);

    List<UsersDTO> toDto(List<Users> users);
    List<Users> fromDto(List<UsersDTO> usersDTO);
}
