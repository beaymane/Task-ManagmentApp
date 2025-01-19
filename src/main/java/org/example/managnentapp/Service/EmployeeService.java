package org.example.managnentapp.Service;

import org.example.managnentapp.Dto.EmployeeDTO;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    EmployeeDTO createEmployee(Long userId , Long departmentId , EmployeeDTO employeeDTO);
    EmployeeDTO updateEmployee(Long userId , Long id, EmployeeDTO employeeDTO);
    void deleteEmployee(Long userId , Long id);
    EmployeeDTO getEmployeeById(Long userId , Long id);
    List<EmployeeDTO> getAllEmployees(Long userId);
    List<EmployeeDTO> searchEmployees(Long userId ,Long demartmentId ,String query);

}
