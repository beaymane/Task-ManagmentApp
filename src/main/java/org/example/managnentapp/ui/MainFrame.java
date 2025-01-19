package org.example.managnentapp.ui;

import net.miginfocom.swing.MigLayout;
import org.example.managnentapp.ui.panels.AuditLogPanel;
import org.example.managnentapp.ui.panels.DepartmentPanel;
import org.example.managnentapp.ui.panels.EmployeePanel;
import org.example.managnentapp.ui.panels.UserPanel;
import org.example.managnentapp.ui.service.UIAuditLogService;
import org.example.managnentapp.ui.service.UIDepartmentService;
import org.example.managnentapp.ui.service.UIEmployeeService;
import org.example.managnentapp.ui.service.UIUserService;

import javax.swing.*;

public class MainFrame extends JFrame {
    private final UIEmployeeService employeeService;
    private final UIDepartmentService departmentService;
    private final UIAuditLogService uiAuditLogService;

    private final UIUserService uiUserservice;


    public MainFrame() {
        this.uiAuditLogService = new UIAuditLogService();
        this.employeeService = new UIEmployeeService();
        this.departmentService = new UIDepartmentService();
        this.uiUserservice = new UIUserService();
        initComponents();
        setupFrame();
    }

    private void setupFrame() {
        setTitle("Employee Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setLayout(new MigLayout("fill"));
        
        JTabbedPane tabbedPane = new JTabbedPane();
        EmployeePanel employeePanel = new EmployeePanel(employeeService, departmentService);
        tabbedPane.addTab("Employees",employeePanel);
        tabbedPane.addTab("Departments", new DepartmentPanel(departmentService));
        AuditLogPanel auditLogPanel = new AuditLogPanel(uiAuditLogService);
        tabbedPane.addTab("logs",auditLogPanel);

        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedComponent() == auditLogPanel) {
                auditLogPanel.onTabSelected(); // Reload the logs when this tab is selected
            }
            if (tabbedPane.getSelectedComponent() == employeePanel) {
                employeePanel.onTabSelected(); // Reload the logs when this tab is selected
            }
        });

        add(tabbedPane, "grow");


    }


} 