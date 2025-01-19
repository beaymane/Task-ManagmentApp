package org.example.managnentapp.ServiceImp;

import org.example.managnentapp.Dto.AuditLogDTO;
import org.example.managnentapp.Mapper.AuditLogDTOMapper;
import org.example.managnentapp.Model.AuditLog;
import org.example.managnentapp.Repository.AuditLogRepository;
import org.example.managnentapp.Service.AuditLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditLogServiceImpl implements AuditLogService {
    private final AuditLogRepository auditLogRepository;
    private final AuditLogDTOMapper auditLogDTOMapper;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepository, AuditLogDTOMapper auditLogDTOMapper) {
        this.auditLogRepository = auditLogRepository;
        this.auditLogDTOMapper = auditLogDTOMapper;
    }

    @Override
    public AuditLogDTO createAuditLog(AuditLogDTO auditLogDTO) {
        AuditLog auditLog = auditLogDTOMapper.toEntity(auditLogDTO);
        AuditLog savedLog = auditLogRepository.save(auditLog);
        return auditLogDTOMapper.toDto(savedLog);
    }

    @Override
    public AuditLogDTO getAuditLogById(Long id) {
        return null;
    }

    @Override
    public List<AuditLogDTO> getAllAuditLogs() {
        List<AuditLog> auditLogs = auditLogRepository.findAll();
        return auditLogDTOMapper.toDtos(auditLogs);
    }

    @Override
    public List<AuditLogDTO> getAuditLogsByEntity(Long entityId, String entityType) {
        return List.of();
    }
}
