package org.example.managnentapp.ui.service;

import org.example.managnentapp.Dto.EmployeeDTO;
import org.springframework.core.ParameterizedTypeReference;

import java.util.List;

public class UIEmployeeService {
    private final ApiClient apiClient;

    public UIEmployeeService() {
        this.apiClient = new ApiClient();
    }

    public List<EmployeeDTO> getAllEmployees() {
        return apiClient.get("/employee/1", new ParameterizedTypeReference<List<EmployeeDTO>>() {});
    }

    public EmployeeDTO createEmployee(Long departmentId, EmployeeDTO employee) {
        return apiClient.post("/employee/" + departmentId +"/1", employee, EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployee(Long employeeId, EmployeeDTO employee) {
        return apiClient.put("/employee/" + employeeId +"/1", employee, EmployeeDTO.class);
    }

    public void deleteEmployee(Long id) {
        apiClient.delete("/employee/" + id +"/1");
    }

    public List<EmployeeDTO> searchEmployees(Long departmentId, String query) {
        StringBuilder path = new StringBuilder("/employee/search/1?");
        if (departmentId != null) {
            path.append("departmentId=").append(departmentId);
        }
        if (query != null && !query.isEmpty()) {
            if (departmentId != null) {
                path.append("&");
            }
            path.append("query=").append(query);
        }
        return apiClient.get(path.toString(), new ParameterizedTypeReference<List<EmployeeDTO>>() {});
    }
} 