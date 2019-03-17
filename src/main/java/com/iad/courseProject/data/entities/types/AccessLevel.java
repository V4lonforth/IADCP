package com.iad.courseProject.data.entities.types;

public enum AccessLevel {
    USER,
    LOGISTICIAN,
    MANAGER,
    ADMINISTRATOR;

    private final String[][] levels = {
            {"ROLE_USER"},
            {"ROLE_LOGISTICIAN", "ROLE_USER"},
            {"ROLE_MANAGER", "ROLE_USER"},
            {"ROLE_ADMINISTRATOR", "ROLE_LOGISTICIAN", "ROLE_MANAGER", "ROLE_USER"}
    };

    public String[] getLevels() {
        return levels[ordinal()];
    }
}
