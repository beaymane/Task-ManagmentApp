package org.example.managnentapp.Enum;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum UserRole {
    HR,
    MANAGER,
    ADMINISTRATOR
}

