# Employee Records Management System

# Employee Records Management System

## Overview

The Employee Records Management System is designed to centralize the management of employee data across departments. It provides role-based access control and ensures data integrity with validation rules, search capabilities, and audit trails.

The system handles the following employee attributes:

---

## Backlog Tasks

1. **Implement CRUD functionality for HR personnel**
    - Design database schema for employee data.
    - Develop backend API endpoints.
    - Create frontend UI for CRUD operations.
2. **Role-based access control**
    - Implement role validation for HR, Managers, and Administrators.
3. **Audit Trail**
    - Develop backend logging for changes to employee records.
    - Create UI to view audit logs.
4. **Search and Filtering**
    - Implement search functionality.
    - Add filtering options to the frontend UI.
5. **Validation Rules**
    - Develop validation logic for unique employee IDs and email formats.
    - Create UI error messages for validation failures.
6. **Reporting Features**
    - Generate reports on employees by department, hire date, and employment status.

**7 . Docker Integration**

- Set up an Oracle SQL database container using Docker (`gvenzl/oracle-xe:latest`).

---

## Future Enhancements

- **Advanced Reporting**: Introduce customizable reporting templates.
- **Data Import/Export**: Enable CSV/Excel import and export of employee records.
- **Integration**: Connect with third-party HR systems for data synchronization.
- **Notifications**: Notify managers and HR personnel of updates or pending tasks.

---

- **Technology Stack**:
    - Backend: Java (Spring Boot)
    - Frontend: Swing (Java UI)
    - Database: Oracle SQL (Docker image: `gvenzl/oracle-xe:latest`)
- **Updated Folder Structure**:
    - `Model`: Domain model classes (entities representing database tables)
    - `Controller`: Handles API endpoints
    - `Service`: Business logic layer
    - `ServiceImpl`: Implementations of Service interfaces
    - `Repository`: Database interactions
    - `DTO`: Data Transfer Objects
    - `Enum`: Enumerations
    - `Mapper`: Classes for mapping between DTOs and Model entities
    - `UI`: Swing-based frontend
        - `Dialogues`: Dialog components
        - `Model`: UI-specific models
        - `Panels`: Panels for different UI sections
        - `Service Panel`: Services specific to UI panels
    - `MainFrame`: Main application frame (entry point for UI)
    - `SwingApp`: Application setup or helper classes
    - `SwingAppLauncher`: Main class to launch the application
