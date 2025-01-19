package org.example.managnentapp.ui.model;



import org.example.managnentapp.Dto.UserDTO;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class UserTableModel extends AbstractTableModel {
    private final String[] columnNames = {"ID", "Username", "Role", "Department"};
    private List<UserDTO> users = new ArrayList<>();

    public void setUsers(List<UserDTO> users) {
        this.users = users;
        fireTableDataChanged();
    }

    public UserDTO getUserAt(int rowIndex) {
        return users.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        UserDTO user = users.get(rowIndex);
        switch (columnIndex) {
            case 0: return user.getId();
            case 1: return user.getUsername();
            case 2: return user.getRole();
            case 3: return user.getDepartmentName();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
