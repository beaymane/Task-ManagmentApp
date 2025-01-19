package org.example.managnentapp.Service;

import org.example.managnentapp.Dto.AuditLogDTO;

import java.util.List;

public interface AuditLogService {
    AuditLogDTO createAuditLog(AuditLogDTO auditLogDTO);
    AuditLogDTO getAuditLogById(Long id);
    List<AuditLogDTO> getAllAuditLogs();
    List<AuditLogDTO> getAuditLogsByEntity(Long entityId, String entityType);

}
