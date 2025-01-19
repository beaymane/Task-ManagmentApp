package org.example.managnentapp.ui.panels;

import net.miginfocom.swing.MigLayout;
import org.example.managnentapp.Dto.EmployeeDTO;
import org.example.managnentapp.Dto.DepartmentDTO;
import org.example.managnentapp.ui.dialogs.EmployeeDialog;
import org.example.managnentapp.ui.model.EmployeeTableModel;
import org.example.managnentapp.ui.service.UIDepartmentService;
import org.example.managnentapp.ui.service.UIEmployeeService;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.util.List;

public class EmployeePanel extends JPanel {
    private final UIEmployeeService employeeService;
    private final UIDepartmentService departmentService;
    private final EmployeeTableModel tableModel;
    private JTable employeeTable;
    private JTextField searchField;
    private JComboBox<String> departmentCombo;
    private final TableRowSorter<EmployeeTableModel> sorter;

    public EmployeePanel(UIEmployeeService employeeService, UIDepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.tableModel = new EmployeeTableModel();
        this.sorter = new TableRowSorter<>(tableModel);
        initComponents();
        loadEmployees();
    }

    private void initComponents() {
        setLayout(new MigLayout("fill", "[grow]", "[][][][grow]"));

        // Search Panel
        JPanel searchPanel = new JPanel(new MigLayout("", "[][grow][][]"));
        searchField = new JTextField(20);
        departmentCombo = new JComboBox<>(new String[]{"All Departments"});
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField, "width 200!");
        searchPanel.add(new JLabel("Department:"));
        searchPanel.add(departmentCombo);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new MigLayout());
        JButton addButton = new JButton("Add Employee");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Table
        employeeTable = new JTable(tableModel);
        employeeTable.setRowSorter(sorter);
        employeeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(employeeTable);

        // Add components
        add(searchPanel, "wrap");
        add(buttonPanel, "wrap");
        add(scrollPane, "grow");

        // Add listeners
        addButton.addActionListener(e -> showEmployeeDialog(null));
        editButton.addActionListener(e -> editSelectedEmployee());
        deleteButton.addActionListener(e -> deleteSelectedEmployee());
        
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { search(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { search(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { search(); }
        });
        
        departmentCombo.addActionListener(e -> search());

        loadDepartments();
    }

    private void loadDepartments() {
        try {
            List<DepartmentDTO> departments = departmentService.getAllDepartments();
            departmentCombo.removeAllItems();
            departmentCombo.addItem("All Departments");
            departments.forEach(dept -> departmentCombo.addItem(dept.getName()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading departments: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadEmployees() {
        try {
            List<EmployeeDTO> employees = employeeService.getAllEmployees();
            tableModel.setEmployees(employees);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading employees: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void search() {
        String query = searchField.getText();
        Object selectedDepartment = departmentCombo.getSelectedItem();
        String department = selectedDepartment != null ? selectedDepartment.toString() : "All Departments";
        Long departmentId = department.equals("All Departments") ? null : 
            getDepartmentIdByName(department);

        try {
            List<EmployeeDTO> employees = employeeService.searchEmployees(departmentId, query);
            tableModel.setEmployees(employees);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error searching employees: " + e.getMessage(), 
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

    private void showEmployeeDialog(EmployeeDTO employee) {
        EmployeeDialog dialog = new EmployeeDialog(
            SwingUtilities.getWindowAncestor(this), 
            employeeService,
            departmentService,
            employee
        );
        dialog.setVisible(true);
        if (dialog.isSuccessful()) {
            loadEmployees();
        }
    }

    private void editSelectedEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            selectedRow = employeeTable.convertRowIndexToModel(selectedRow);
            EmployeeDTO employee = tableModel.getEmployeeAt(selectedRow);
            showEmployeeDialog(employee);
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee to edit");
        }
    }

    private void deleteSelectedEmployee() {
        int selectedRow = employeeTable.getSelectedRow();
        if (selectedRow >= 0) {
            selectedRow = employeeTable.convertRowIndexToModel(selectedRow);
            EmployeeDTO employee = tableModel.getEmployeeAt(selectedRow);
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete employee: " + employee.getFullName() + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    employeeService.deleteEmployee(employee.getId());
                    loadEmployees();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, 
                        "Error deleting employee: " + e.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an employee to delete");
        }
    }

    public void onTabSelected() {
        loadDepartments(); // Refresh logs when the tab is selected
    }


} 