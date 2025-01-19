package org.example.managnentapp.ui.model;

import org.example.managnentapp.Dto.AuditLogDTO;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class AuditLogTableModel extends AbstractTableModel {

    private final String[] columnNames = {"ID", "Entity ID", "Entity Type", "Action", "Timestamp", "Logger", "Details"};
    private List<AuditLogDTO> auditLogs = new ArrayList<>();

    // Method to set the list of audit logs
    public void setAuditLogs(List<AuditLogDTO> auditLogs) {
        this.auditLogs = auditLogs;
        fireTableDataChanged(); // Refresh table data
    }

    // Method to get the audit log at a specific row
    public AuditLogDTO getAuditLogAt(int rowIndex) {
        return auditLogs.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return auditLogs.size(); // Return number of rows (audit logs)
    }

    @Override
    public int getColumnCount() {
        return columnNames.length; // Return number of columns
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AuditLogDTO auditLog = auditLogs.get(rowIndex);
        switch (columnIndex) {
            case 0: return auditLog.getId();
            case 1: return auditLog.getEntityId();
            case 2: return auditLog.getEntityType();
            case 3: return auditLog.getAction();
            case 4: return auditLog.getTimestamp();
            case 5: return auditLog.getLogger();
            case 6: return auditLog.getDetails();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column]; // Return column name
    }
}
