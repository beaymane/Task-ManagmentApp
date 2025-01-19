package org.example.managnentapp.ui.panels;

import org.example.managnentapp.Dto.UserDTO;
import org.example.managnentapp.ui.dialogs.UserDialog;
import org.example.managnentapp.ui.service.UIUserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserPanel extends JPanel {
    private final UIUserService userService;
    private JTable userTable;
    private DefaultTableModel tableModel;

    public UserPanel(UIUserService userService) {
        this.userService = userService;
        setLayout(new BorderLayout());
        initComponents();
        loadUsers();
    }

    private void initComponents() {
        // Initialize the table
        tableModel = new DefaultTableModel(new Object[]{"ID", "Username", "Role", "Department Name"}, 0);
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);

        // Buttons for adding, editing, and deleting users
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addButton = new JButton("Add User");
        JButton editButton = new JButton("Edit User");
        JButton deleteButton = new JButton("Delete User");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Adding components to the panel
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners for buttons
        addButton.addActionListener(e -> openUserDialog(null));  // Add new user
        editButton.addActionListener(e -> openUserDialog(getSelectedUser()));  // Edit selected user
        deleteButton.addActionListener(e -> deleteUser());  // Delete selected user
    }

    private void loadUsers() {
        // Fetch the list of users from the user service
        List<UserDTO> users = userService.getAllUsers();
        tableModel.setRowCount(0);  // Clear existing data

        // Add users to the table
        for (UserDTO user : users) {
            tableModel.addRow(new Object[]{
                    user.getId(),
                    user.getUsername(),
                    user.getRole(),
                    user.getDepartmentName()
            });
        }
    }

    private UserDTO getSelectedUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            Long userId = (Long) tableModel.getValueAt(selectedRow, 0);
            return userService.getUserById(userId);  // Fetch the user details from the service
        }
        JOptionPane.showMessageDialog(this, "Please select a user to edit.");
        return null;
    }

    private void openUserDialog(UserDTO user) {
        UserDialog userDialog = new UserDialog(SwingUtilities.getWindowAncestor(this), userService, user);
        userDialog.setVisible(true);

        if (userDialog.isSuccessful()) {
            loadUsers();  // Reload the user list after adding or editing a user
        }
    }

    private void deleteUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            Long userId = (Long) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Delete User", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                userService.deleteUser(userId);
                loadUsers();  // Reload the user list after deletion
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
        }
    }
}
