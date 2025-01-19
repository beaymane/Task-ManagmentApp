package org.example.managnentapp.Mapper;

import org.example.managnentapp.Dto.AuditLogDTO;
import org.example.managnentapp.Model.AuditLog;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuditLogDTOMapper implements EntityDTOMapper<AuditLog , AuditLogDTO>{
    private final ModelMapper modelMapper;

    public AuditLogDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuditLogDTO toDto(AuditLog entity) {
        return modelMapper.map(entity, AuditLogDTO.class);
    }

    @Override
    public AuditLog toEntity(AuditLogDTO dto) {
        return modelMapper.map(dto, AuditLog.class);
    }
}
