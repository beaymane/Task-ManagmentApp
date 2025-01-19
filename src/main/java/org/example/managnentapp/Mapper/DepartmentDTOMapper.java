package org.example.managnentapp.Mapper;

import org.example.managnentapp.Dto.DepartmentDTO;
import org.example.managnentapp.Model.Department;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DepartmentDTOMapper implements EntityDTOMapper<Department , DepartmentDTO>{
    private final ModelMapper modelMapper;

    public DepartmentDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public DepartmentDTO toDto(Department entity) {
        return modelMapper.map(entity, DepartmentDTO.class);
    }

    @Override
    public Department toEntity(DepartmentDTO dto) {
        return modelMapper.map(dto, Department.class);
    }
}
