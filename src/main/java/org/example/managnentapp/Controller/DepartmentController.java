package org.example.managnentapp.Controller;

import org.example.managnentapp.Dto.DepartmentDTO;
import org.example.managnentapp.Dto.EmployeeDTO;
import org.example.managnentapp.ServiceImp.DepartmentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/department")
public class DepartmentController {

    private final DepartmentServiceImpl departmentService;

    public DepartmentController(DepartmentServiceImpl departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(
            @RequestBody DepartmentDTO departmentDTO
    ) {
        DepartmentDTO createdDep = departmentService.createDepartment(departmentDTO);
        return ResponseEntity.ok(createdDep);
    }

    @PutMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable Long departmentId, @RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO updatedDep = departmentService.updateDepartment(departmentId, departmentDTO);
        return ResponseEntity.ok(updatedDep);
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        List<DepartmentDTO> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }
    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentDTO> getEmployeeById(@PathVariable Long departmentId) {
        DepartmentDTO department = departmentService.getDepartmentById(departmentId);
        return ResponseEntity.ok(department);
    }


    @DeleteMapping("/{departmentId}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long departmentId) {
        departmentService.deleteDepartment(departmentId);
        return new ResponseEntity<>("Department deleted successfully", HttpStatus.OK);
    }

}
