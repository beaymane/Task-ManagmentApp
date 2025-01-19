package org.example.managnentapp.ui.panels;

import net.miginfocom.swing.MigLayout;
import org.example.managnentapp.Dto.AuditLogDTO;
import org.example.managnentapp.ui.model.AuditLogTableModel;
import org.example.managnentapp.ui.service.UIAuditLogService;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;
import java.util.List;

public class AuditLogPanel extends JPanel {
    private final UIAuditLogService auditLogService;
    private final AuditLogTableModel tableModel;
    private JTable auditLogTable;
    private JTextField searchField;
    private final TableRowSorter<AuditLogTableModel> sorter;

    public AuditLogPanel(UIAuditLogService auditLogService) {
        this.auditLogService = auditLogService;
        this.tableModel = new AuditLogTableModel();
        this.sorter = new TableRowSorter<>(tableModel);
        initComponents();
        loadAuditLogs();
    }

    private void initComponents() {

        System.out.println(" im here ");
        setLayout(new MigLayout("fill", "[grow]", "[][][][grow]"));

        // Search Panel
        JPanel searchPanel = new JPanel(new MigLayout("", "[][]"));
        searchField = new JTextField(20);
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField, "width 200!");

        // Table
        auditLogTable = new JTable(tableModel);
        auditLogTable.setRowSorter(sorter);
        auditLogTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(auditLogTable);

        // Add components
        add(searchPanel, "wrap");
        add(scrollPane, "grow");

        // Add listeners
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filter();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filter();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filter();
            }
        });
    }

    private void loadAuditLogs() {
        try {
            List<AuditLogDTO> auditLogs = auditLogService.getAllAuditLogs();
            tableModel.setAuditLogs(auditLogs);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error loading audit logs: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filter() {
        String text = searchField.getText();
        if (text.trim().length() == 0) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }

    public void onTabSelected() {
        loadAuditLogs(); // Refresh logs when the tab is selected
    }
}
