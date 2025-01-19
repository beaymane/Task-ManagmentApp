package org.example.managnentapp.Controller;

import org.example.managnentapp.Dto.EmployeeDTO;
import org.example.managnentapp.ServiceImp.EmployeeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    private final EmployeeServiceImpl employeeService;

    public EmployeeController(EmployeeServiceImpl employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/{departmentId}/{userId}")
    public ResponseEntity<EmployeeDTO> createEmployee(
            @RequestBody EmployeeDTO employeeDTO ,
            @PathVariable Long departmentId ,
            @PathVariable Long userId
    ) {
        EmployeeDTO createdEmployee = employeeService.createEmployee(userId , departmentId , employeeDTO);
        return ResponseEntity.ok(createdEmployee);
    }

    @PutMapping("/{employeeId}/{userId}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable Long employeeId,
            @PathVariable Long userId ,
            @RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO updatedEmployee = employeeService.updateEmployee(userId , employeeId , employeeDTO);
        return ResponseEntity.ok(updatedEmployee);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@PathVariable Long userId) {
        List<EmployeeDTO> employees = employeeService.getAllEmployees(userId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/search/{userId}")
    public ResponseEntity<List<EmployeeDTO>> searchEmployees(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Long departmentId ,
            @PathVariable Long userId
    ) {
        List<EmployeeDTO> employees = employeeService.searchEmployees(userId , departmentId , query);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{employeeId}/{userId}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(
            @PathVariable Long employeeId,
            @PathVariable Long userId
    ) {
        EmployeeDTO employee = employeeService.getEmployeeById(userId , employeeId);
        return ResponseEntity.ok(employee);
    }

    @DeleteMapping("/{employeeId}/{userId}")
    public ResponseEntity<String> deleteEmployee(
            @PathVariable Long employeeId ,
            @PathVariable Long userId
    ) {
        employeeService.deleteEmployee(userId , employeeId);
        return new ResponseEntity<>("Employee deleted successfully", HttpStatus.OK);
    }

}
