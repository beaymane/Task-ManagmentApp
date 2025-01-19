package org.example.managnentapp.Repository;

import org.example.managnentapp.Model.Department;
import org.example.managnentapp.Model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartment(Department department);
    List<Employee> findByFullNameContainingIgnoreCase(String name);
    List<Employee> findByJobTitleContainingIgnoreCase(String jobTitle);
    List<Employee> findByEmployeeIdContainingIgnoreCase(String employeeId);

}
