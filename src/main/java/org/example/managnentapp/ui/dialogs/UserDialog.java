package org.example.managnentapp.ui.dialogs;

import net.miginfocom.swing.MigLayout;
import org.example.managnentapp.Dto.UserDTO;
import org.example.managnentapp.Enum.UserRole;
import org.example.managnentapp.ui.service.UIUserService;

import javax.swing.*;
import java.awt.*;

public class UserDialog extends JDialog {
    private final UIUserService userService;
    private final UserDTO user;
    private boolean successful = false;

    private JTextField usernameField;
    private JComboBox<UserRole> roleComboBox;
    private JComboBox<String> departmentComboBox;

    public UserDialog(Window owner, UIUserService userService, UserDTO user) {
        super(owner, user == null ? "Add User" : "Edit User", ModalityType.APPLICATION_MODAL);
        this.userService = userService;
        this.user = user != null ? user : new UserDTO();
        initComponents();
        loadUserData();
    }

    private void initComponents() {
        setLayout(new MigLayout("fill", "[][grow]", "[][]push[]"));

        usernameField = new JTextField(20);
        roleComboBox = new JComboBox<>(UserRole.values());
        departmentComboBox = new JComboBox<>(new String[]{"Department 1", "Department 2", "Department 3"}); // Mock departments

        add(new JLabel("Username:"));
        add(usernameField, "growx, wrap");
        add(new JLabel("Role:"));
        add(roleComboBox, "growx, wrap");
        add(new JLabel("Department:"));
        add(departmentComboBox, "growx, wrap");

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

    private void loadUserData() {
        if (user.getId() != null) {
            usernameField.setText(user.getUsername());
            roleComboBox.setSelectedItem(user.getRole());
            departmentComboBox.setSelectedItem(user.getDepartmentName());
        }
    }

    private void save() {
        try {
            user.setUsername(usernameField.getText());
            user.setRole((UserRole) roleComboBox.getSelectedItem());
            user.setDepartmentName((String) departmentComboBox.getSelectedItem());

            if (user.getId() == null) {
                userService.createUser(user.getDepartmentId(), user);
            } else {
                userService.updateUser(user.getId(), user);
            }
            successful = true;
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving user: " + e.getMessage());
        }
    }

    public boolean isSuccessful() {
        return successful;
    }
}
