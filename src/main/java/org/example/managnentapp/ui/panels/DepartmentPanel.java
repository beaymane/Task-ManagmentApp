package org.example.managnentapp.ui.panels;

import net.miginfocom.swing.MigLayout;
import org.example.managnentapp.Dto.DepartmentDTO;
import org.example.managnentapp.ui.model.DepartmentTableModel;
import org.example.managnentapp.ui.service.UIDepartmentService;
import org.example.managnentapp.ui.dialogs.DepartmentDialog;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.FlowLayout;
import java.util.List;

public class DepartmentPanel extends JPanel {
    private final UIDepartmentService departmentService;
    private final DepartmentTableModel tableModel;
    private JTable departmentTable;
    private JTextField searchField;
    private final TableRowSorter<DepartmentTableModel> sorter;

    public DepartmentPanel(UIDepartmentService departmentService) {
        this.departmentService = departmentService;
        this.tableModel = new DepartmentTableModel();
        this.sorter = new TableRowSorter<>(tableModel);
        initComponents();
        loadDepartments();
    }

    private void initComponents() {
        setLayout(new MigLayout("fill", "[grow]", "[][][][grow]"));

        // Search Panel
        JPanel searchPanel = new JPanel(new MigLayout("", "[][]"));
        searchField = new JTextField(20);
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField, "width 200!");

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new MigLayout());
        JButton addButton = new JButton("Add Department");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Table
        departmentTable = new JTable(tableModel);
        departmentTable.setRowSorter(sorter);
        departmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(departmentTable);

        // Add components
        add(searchPanel, "wrap");
        add(buttonPanel, "wrap");
        add(scrollPane, "grow");

        // Add listeners
        addButton.addActionListener(e -> showDepartmentDialog(null));
        editButton.addActionListener(e -> editSelectedDepartment());
        deleteButton.addActionListener(e -> deleteSelectedDepartment());
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
        });
    }

    private void loadDepartments() {
        try {
            List<DepartmentDTO> departments = departmentService.getAllDepartments();
            tableModel.setDepartments(departments);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading departments: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showDepartmentDialog(DepartmentDTO department) {
        DepartmentDialog dialog = new DepartmentDialog(
            SwingUtilities.getWindowAncestor(this), 
            departmentService, 
            department
        );
        dialog.setVisible(true);
        if (dialog.isSuccessful()) {
            loadDepartments();
        }
    }

    private void editSelectedDepartment() {
        int selectedRow = departmentTable.getSelectedRow();
        if (selectedRow >= 0) {
            selectedRow = departmentTable.convertRowIndexToModel(selectedRow);
            DepartmentDTO department = tableModel.getDepartmentAt(selectedRow);
            showDepartmentDialog(department);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a department to edit");
        }
    }

    private void deleteSelectedDepartment() {
        int selectedRow = departmentTable.getSelectedRow();
        if (selectedRow >= 0) {
            selectedRow = departmentTable.convertRowIndexToModel(selectedRow);
            DepartmentDTO department = tableModel.getDepartmentAt(selectedRow);
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete department: " + department.getName() + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    departmentService.deleteDepartment(department.getId());
                    loadDepartments();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, 
                        "Error deleting department: " + e.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a department to delete");
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
} 