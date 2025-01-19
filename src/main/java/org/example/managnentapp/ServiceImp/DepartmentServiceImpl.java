package org.example.managnentapp.ServiceImp;

import org.example.managnentapp.Dto.DepartmentDTO;
import org.example.managnentapp.Mapper.DepartmentDTOMapper;
import org.example.managnentapp.Model.Department;
import org.example.managnentapp.Repository.DepartmentRepository;
import org.example.managnentapp.Service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentDTOMapper departmentDTOMapper;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, DepartmentDTOMapper departmentDTOMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentDTOMapper = departmentDTOMapper;
    }

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = departmentDTOMapper.toEntity(departmentDTO);
        departmentRepository.save(department);
        return departmentDTOMapper.toDto(department);
    }

    @Override
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        existingDepartment.setName(departmentDTO.getName());
        existingDepartment.setDescription(departmentDTO.getDescription());

        Department updatedDepartment = departmentRepository.save(existingDepartment);
        return departmentDTOMapper.toDto(updatedDepartment);
    }

    @Override
    public void deleteDepartment(Long id) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        departmentRepository.delete(existingDepartment);
    }

    @Override
    public DepartmentDTO getDepartmentById(Long id) {
        Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        return departmentDTOMapper.toDto(existingDepartment);
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departmentDTOMapper.toDtos(departments);
    }
}
