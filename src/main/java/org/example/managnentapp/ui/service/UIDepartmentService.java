package org.example.managnentapp.ui.service;

import org.example.managnentapp.Dto.DepartmentDTO;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;

public class UIDepartmentService {
    private final ApiClient apiClient;

    public UIDepartmentService() {
        this.apiClient = new ApiClient();
    }

    public List<DepartmentDTO> getAllDepartments() {
        return apiClient.get("/department", new ParameterizedTypeReference<List<DepartmentDTO>>() {});
    }

    public DepartmentDTO createDepartment(DepartmentDTO department) {
        return apiClient.post("/department", department, DepartmentDTO.class);
    }

    public DepartmentDTO updateDepartment(Long id, DepartmentDTO department) {
        return apiClient.put("/department/" + id, department, DepartmentDTO.class);
    }

    public void deleteDepartment(Long id) {
        apiClient.delete("/department/" + id);
    }

    public DepartmentDTO getDepartmentById(Long id) {
        return apiClient.get("/department/" + id, DepartmentDTO.class);
    }
} 