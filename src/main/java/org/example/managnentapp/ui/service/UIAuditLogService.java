package org.example.managnentapp.ui.service;
import org.example.managnentapp.Dto.AuditLogDTO;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class UIAuditLogService {
    private final ApiClient apiClient;

    public UIAuditLogService() {
        this.apiClient = new ApiClient(); // Assume this is the same ApiClient used for other requests
    }

    public List<AuditLogDTO> getAllAuditLogs() {
        return apiClient.get("/logs", new ParameterizedTypeReference<List<AuditLogDTO>>() {});
    }
}
