package org.example.managnentapp.Controller;

import org.example.managnentapp.Dto.AuditLogDTO;
import org.example.managnentapp.Dto.EmployeeDTO;
import org.example.managnentapp.ServiceImp.AuditLogServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/logs")
public class AuditLogController {
    private final AuditLogServiceImpl auditLogService;

    public AuditLogController(AuditLogServiceImpl auditLogService) {
        this.auditLogService = auditLogService;
    }

    @GetMapping
    public ResponseEntity<List<AuditLogDTO>> getAllLogs() {
        List<AuditLogDTO> auditLogDTOS = auditLogService.getAllAuditLogs();
        return ResponseEntity.ok(auditLogDTOS);
    }

}
