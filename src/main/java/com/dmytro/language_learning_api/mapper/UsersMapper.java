package com.dmytro.language_learning_api.mapper;

import com.dmytro.language_learning_api.dto.UsersDTO;
import com.dmytro.language_learning_api.model.Users;
import org.springframework.stereotype.Component;

@Component
public class UsersMapper {
    public UsersDTO toDto(Users user) {
        return new UsersDTO(
                user.getId(),
                user.getEmail(),
                user.getPassword(), // TODO: reemplazar por null
                user.getUsername()
        );
    }

    public Users fromDto(UsersDTO usersDTO) {
        return new Users(
                usersDTO.id(),
                usersDTO.email(),
                usersDTO.password(), // TODO: reemplazar por null
                usersDTO.username()
        );
    }
}
