package org.example.managnentapp.ui.dialogs;

import net.miginfocom.swing.MigLayout;
import org.example.managnentapp.Dto.DepartmentDTO;
import org.example.managnentapp.Dto.EmployeeDTO;
import org.example.managnentapp.Enum.EmploymentStatus;
import org.example.managnentapp.ui.service.UIDepartmentService;
import org.example.managnentapp.ui.service.UIEmployeeService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class EmployeeDialog extends JDialog {
    private final UIEmployeeService employeeService;
    private final UIDepartmentService departmentService;
    private final EmployeeDTO employee;
    private boolean successful = false;

    private JTextField nameField;
    private JTextField jobTitleField;
    private JComboBox<String> departmentCombo;
    private JComboBox<EmploymentStatus> statusCombo;

    public EmployeeDialog(Window owner, UIEmployeeService employeeService, 
            UIDepartmentService departmentService, EmployeeDTO employee) {
        super(owner, employee == null ? "Add Employee" : "Edit Employee", 
                ModalityType.APPLICATION_MODAL);
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.employee = employee != null ? employee : new EmployeeDTO();
        initComponents();
        loadEmployee();
    }

    private void initComponents() {
        setLayout(new MigLayout("fill", "[][grow]", "[][][][]push[]"));

        // Fields
        nameField = new JTextField(20);
        jobTitleField = new JTextField(20);
        departmentCombo = new JComboBox<>();
        statusCombo = new JComboBox<>(EmploymentStatus.values());

        // Load departments
        loadDepartments();

        // Add components
        add(new JLabel("Full Name:"));
        add(nameField, "growx, wrap");
        add(new JLabel("Job Title:"));
        add(jobTitleField, "growx, wrap");
        add(new JLabel("Department:"));
        add(departmentCombo, "growx, wrap");
        add(new JLabel("Status:"));
        add(statusCombo, "growx, wrap");

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, "span 2, growx");

        // Add listeners
        saveButton.addActionListener(e -> save());
        cancelButton.addActionListener(e -> dispose());

        // Dialog properties
        setSize(400, 300);
        setLocationRelativeTo(getOwner());
    }

    private void loadDepartments() {
        try {
            List<DepartmentDTO> departments = departmentService.getAllDepartments();
            departmentCombo.removeAllItems();
            departments.forEach(dept -> departmentCombo.addItem(dept.getName()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error loading departments: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadEmployee() {
        if (employee.getId() != null) {
            nameField.setText(employee.getFullName());
            jobTitleField.setText(employee.getJobTitle());
            departmentCombo.setSelectedItem(employee.getDepartmentName());
            statusCombo.setSelectedItem(employee.getEmploymentStatus());
        }
    }

    private void save() {
        try {
            employee.setFullName(nameField.getText());
            employee.setJobTitle(jobTitleField.getText());
            employee.setEmploymentStatus((EmploymentStatus) statusCombo.getSelectedItem());

            String departmentName = (String) departmentCombo.getSelectedItem();
            Long departmentId = getDepartmentIdByName(departmentName);

            if (employee.getId() == null) {
                employeeService.createEmployee(departmentId, employee);
            } else {
                employeeService.updateEmployee(employee.getId(), employee);
            }
            successful = true;
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error saving employee: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private Long getDepartmentIdByName(String name) {
        try {
            List<DepartmentDTO> departments = departmentService.getAllDepartments();
            return departments.stream()
                .filter(dept -> dept.getName().equals(name))
                .map(DepartmentDTO::getId)
                .findFirst()
                .orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isSuccessful() {
        return successful;
    }
} 