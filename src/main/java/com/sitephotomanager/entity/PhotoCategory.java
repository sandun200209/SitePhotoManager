package com.sitephotomanager.entity;

public enum PhotoCategory {
    BEFORE_WORK("Before Work"),
    DURING_WORK("During Work"),
    AFTER_WORK("After Work"),
    DEFECT("Defect"),
    INSPECTION("Inspection"),
    TESTING("Testing"),
    OTHER("Other");

    private final String displayName;

    PhotoCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
