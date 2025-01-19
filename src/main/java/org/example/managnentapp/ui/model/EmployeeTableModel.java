package org.example.managnentapp.ui.model;

import org.example.managnentapp.Dto.EmployeeDTO;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class EmployeeTableModel extends AbstractTableModel {
    private final List<EmployeeDTO> employees = new ArrayList<>();
    private final String[] columnNames = {"ID", "Name", "Department", "Job Title", "Status"};

    public void setEmployees(List<EmployeeDTO> employees) {
        this.employees.clear();
        this.employees.addAll(employees);
        fireTableDataChanged();
    }

    public EmployeeDTO getEmployeeAt(int row) {
        return employees.get(row);
    }

    @Override
    public int getRowCount() {
        return employees.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        EmployeeDTO employee = employees.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> employee.getId();
            case 1 -> employee.getFullName();
            case 2 -> employee.getDepartmentName();
            case 3 -> employee.getJobTitle();
            case 4 -> employee.getEmploymentStatus();
            default -> null;
        };
    }
} 