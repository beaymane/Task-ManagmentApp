package org.example.managnentapp.ServiceImp;

import org.example.managnentapp.Dto.AuditLogDTO;
import org.example.managnentapp.Dto.EmployeeDTO;
import org.example.managnentapp.Dto.UserDTO;
import org.example.managnentapp.Enum.UserRole;
import org.example.managnentapp.Mapper.EmployeeDTOMapper;
import org.example.managnentapp.Model.Department;
import org.example.managnentapp.Model.Employee;
import org.example.managnentapp.Model.User;
import org.example.managnentapp.Repository.DepartmentRepository;
import org.example.managnentapp.Repository.EmployeeRepository;
import org.example.managnentapp.Service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeDTOMapper employeeDTOMapper;
    private final UserServiceImpl userService;
    private final AuditLogServiceImpl auditLogService;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, EmployeeDTOMapper employeeDTOMapper, UserServiceImpl userService, AuditLogServiceImpl auditLogService) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.employeeDTOMapper = employeeDTOMapper;
        this.userService = userService;
        this.auditLogService = auditLogService;
    }

    @Override
    public EmployeeDTO createEmployee(Long userId , Long departmentId , EmployeeDTO employeeDTO) {
        UserDTO user = userService.getUserById(userId);

        if (user.getRole() != UserRole.HR && user.getRole() != UserRole.ADMINISTRATOR) {
            throw new RuntimeException("You do not have permission to perform this action");
        }

        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        Employee employee = employeeDTOMapper.toEntity(employeeDTO);
        employee.setDepartment(department);
        Employee createdEmployee = employeeRepository.save(employee);
        AuditLogDTO auditLogDTO = new AuditLogDTO();
        auditLogDTO.setAction("Create");
        auditLogDTO.setLogger(user.getUsername());
        auditLogDTO.setEntityId(createdEmployee.getId());
        auditLogDTO.setEntityType(Employee.class.getSimpleName());
        auditLogDTO.setDetails("details");
        auditLogService.createAuditLog(auditLogDTO);
        return employeeDTOMapper.toDto(createdEmployee);
    }

    @Override
    public EmployeeDTO updateEmployee(Long userId , Long id, EmployeeDTO employeeDTO) {
        UserDTO user = userService.getUserById(userId);

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));



        if (
                user.getRole() != UserRole.HR &&
                user.getRole() != UserRole.ADMINISTRATOR &&
                        (user.getRole() != UserRole.MANAGER || user.getDepartmentId() != existingEmployee.getDepartment().getId())

        ) {
            throw new RuntimeException("You do not have permission to perform this action");
        }


        existingEmployee.setEmployeeId(employeeDTO.getEmployeeId());
        existingEmployee.setFullName(employeeDTO.getFullName());
        existingEmployee.setJobTitle(employeeDTO.getJobTitle());
        existingEmployee.setHireDate(employeeDTO.getHireDate());
        existingEmployee.setEmploymentStatus(employeeDTO.getEmploymentStatus());
        existingEmployee.setContactInformation(employeeDTO.getContactInformation());
        existingEmployee.setAddress(employeeDTO.getAddress());

        if (employeeDTO.getDepartmentId() != null
                && existingEmployee.getDepartment().getId() != employeeDTO.getDepartmentId()
        ) {
            Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            existingEmployee.setDepartment(department);
        }

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        AuditLogDTO auditLogDTO = new AuditLogDTO();
        auditLogDTO.setAction("Update");
        auditLogDTO.setLogger(user.getUsername());
        auditLogDTO.setEntityId(updatedEmployee.getId());
        auditLogDTO.setEntityType(Employee.class.getSimpleName());
        auditLogDTO.setDetails("details");
        auditLogService.createAuditLog(auditLogDTO);

        return employeeDTOMapper.toDto(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long userId , Long id) {
        UserDTO user = userService.getUserById(userId);

        if (user.getRole() != UserRole.HR && user.getRole() != UserRole.ADMINISTRATOR) {
            throw new RuntimeException("You do not have permission to perform this action");
        }
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employeeRepository.deleteById(id);
        AuditLogDTO auditLogDTO = new AuditLogDTO();
        auditLogDTO.setAction("Delete");
        auditLogDTO.setLogger(user.getUsername());
        auditLogDTO.setEntityId(existingEmployee.getId());
        auditLogDTO.setEntityType(Employee.class.getSimpleName());
        auditLogDTO.setDetails("details");
        auditLogService.createAuditLog(auditLogDTO);

    }

    @Override
    public EmployeeDTO getEmployeeById(Long userId , Long id) {
        UserDTO user = userService.getUserById(userId);

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found by id"));

        if (
                user.getRole() != UserRole.HR &&
                        user.getRole() != UserRole.ADMINISTRATOR &&
                        (user.getRole() != UserRole.MANAGER || user.getDepartmentId() != existingEmployee.getDepartment().getId())

        ) {
            throw new RuntimeException("You do not have permission to perform this action");
        }

        return employeeDTOMapper.toDto(existingEmployee);
    }

    @Override
    public List<EmployeeDTO> getAllEmployees(Long userId) {
        UserDTO user = userService.getUserById(userId);

        Department department = departmentRepository.findById(user.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));


        if (user.getRole() != UserRole.HR &&
                user.getRole() != UserRole.ADMINISTRATOR &&
                user.getRole() == UserRole.MANAGER
        ) {
            return employeeDTOMapper.toDtos(employeeRepository.findByDepartment(department));
        }

        List<Employee> employeeList = employeeRepository.findAll();

        return employeeDTOMapper.toDtos(employeeList);
    }

    @Override
    public List<EmployeeDTO> searchEmployees(Long userId ,Long departmentId, String query) {
        UserDTO user = userService.getUserById(userId);

        // Managers should only search within their own department
        if (user.getRole() == UserRole.MANAGER) {
            departmentId = user.getDepartmentId();
        }


        // If no query and no departmentId are provided, return all employees
        if ((query == null || query.isEmpty()) && departmentId == null) {
            return employeeDTOMapper.toDtos(employeeRepository.findAll());
        }

        // Initialize result lists
        List<Employee> queryResults = new ArrayList<>();
        List<Employee> departmentResults = new ArrayList<>();

        // Search by query if provided
        if (query != null && !query.isEmpty()) {
            queryResults.addAll(employeeRepository.findByFullNameContainingIgnoreCase(query));
            queryResults.addAll(employeeRepository.findByJobTitleContainingIgnoreCase(query));
            queryResults.addAll(employeeRepository.findByEmployeeIdContainingIgnoreCase(query));
        }

        // Filter by department if provided
        if (departmentId != null) {
            Department department = departmentRepository.findById(departmentId)
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            departmentResults.addAll(employeeRepository.findByDepartment(department));
        }

        // Retain only common employees if both query and departmentId are provided
        if (query != null && !query.isEmpty() && departmentId != null) {
            queryResults.retainAll(departmentResults); // Retain only common elements
        } else if (departmentId != null) {
            // If only departmentId is provided, use departmentResults
            queryResults = departmentResults;
        }

        // Map results to DTO and return
        return employeeDTOMapper.toDtos(queryResults.stream().distinct().toList());
    }

}
