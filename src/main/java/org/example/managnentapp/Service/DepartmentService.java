package org.example.managnentapp.Service;

import org.example.managnentapp.Dto.DepartmentDTO;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    DepartmentDTO createDepartment(DepartmentDTO departmentDTO);
    DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO);
    void deleteDepartment(Long id);
    DepartmentDTO getDepartmentById(Long id);
    List<DepartmentDTO> getAllDepartments();

}
