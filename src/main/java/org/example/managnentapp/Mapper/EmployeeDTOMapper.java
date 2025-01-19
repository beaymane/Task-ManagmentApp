package org.example.managnentapp.Mapper;

import org.example.managnentapp.Dto.EmployeeDTO;
import org.example.managnentapp.Model.Employee;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDTOMapper implements EntityDTOMapper<Employee, EmployeeDTO> {
    private final ModelMapper modelMapper;

    public EmployeeDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public EmployeeDTO toDto(Employee entity) {
        EmployeeDTO returnDTO = modelMapper.map(entity, EmployeeDTO.class);
        System.out.println(returnDTO);
        return returnDTO;
    }

    @Override
    public Employee toEntity(EmployeeDTO dto) {
        Employee returnEntity = modelMapper.map(dto, Employee.class);
        System.out.println(returnEntity);
        return returnEntity;
    }
}
