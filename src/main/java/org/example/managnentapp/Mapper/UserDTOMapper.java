package org.example.managnentapp.Mapper;

import org.example.managnentapp.Dto.UserDTO;
import org.example.managnentapp.Model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserDTOMapper implements EntityDTOMapper<User , UserDTO> {

    private final ModelMapper modelMapper;

    public UserDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDTO toDto(User entity) {
        return modelMapper.map(entity, UserDTO.class);
    }

    @Override
    public User toEntity(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }
}
