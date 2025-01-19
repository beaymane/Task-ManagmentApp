package org.example.managnentapp.ui.model;

import org.example.managnentapp.Dto.DepartmentDTO;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class DepartmentTableModel extends AbstractTableModel {
    private final List<DepartmentDTO> departments = new ArrayList<>();
    private final String[] columnNames = {"ID", "Name", "Description"};

    public void setDepartments(List<DepartmentDTO> departments) {
        this.departments.clear();
        this.departments.addAll(departments);
        fireTableDataChanged();
    }

    public DepartmentDTO getDepartmentAt(int row) {
        return departments.get(row);
    }

    @Override
    public int getRowCount() {
        return departments.size();
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
        DepartmentDTO department = departments.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> department.getId();
            case 1 -> department.getName();
            case 2 -> department.getDescription();
            default -> null;
        };
    }
} 