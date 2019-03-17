package com.iad.courseProject.jsf;

public enum SetOwnerResult {
    OK("success"),
    OWNER_EXISTS("owner_exists"),
    REGION_NOT_FOUND("region_not_found");

    private final String value;

    SetOwnerResult(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
