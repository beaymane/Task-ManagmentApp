package org.example.managnentapp.ui.dialogs;

import net.miginfocom.swing.MigLayout;
import org.example.managnentapp.Dto.DepartmentDTO;
import org.example.managnentapp.ui.service.UIDepartmentService;

import javax.swing.*;
import java.awt.*;

public class DepartmentDialog extends JDialog {
    private final UIDepartmentService departmentService;
    private final DepartmentDTO department;
    private boolean successful = false;

    private JTextField nameField;
    private JTextArea descriptionArea;

    public DepartmentDialog(Window owner, UIDepartmentService departmentService, DepartmentDTO department) {
        super(owner, department == null ? "Add Department" : "Edit Department", ModalityType.APPLICATION_MODAL);
        this.departmentService = departmentService;
        this.department = department != null ? department : new DepartmentDTO();
        initComponents();
        loadDepartment();
    }

    private void initComponents() {
        setLayout(new MigLayout("fill", "[][grow]", "[][]push[]"));
        
        nameField = new JTextField(20);
        descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        
        add(new JLabel("Name:"));
        add(nameField, "growx, wrap");
        add(new JLabel("Description:"));
        add(new JScrollPane(descriptionArea), "growx, wrap");
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, "span 2, growx");

        saveButton.addActionListener(e -> save());
        cancelButton.addActionListener(e -> dispose());

        setSize(400, 300);
        setLocationRelativeTo(getOwner());
    }

    private void loadDepartment() {
        if (department.getId() != null) {
            nameField.setText(department.getName());
            descriptionArea.setText(department.getDescription());
        }
    }

    private void save() {
        try {
            department.setName(nameField.getText());
            department.setDescription(descriptionArea.getText());
            
            if (department.getId() == null) {
                departmentService.createDepartment(department);
            } else {
                departmentService.updateDepartment(department.getId(), department);
            }
            successful = true;
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving department: " + e.getMessage());
        }
    }

    public boolean isSuccessful() {
        return successful;
    }
} 