package com.tracker.placement.model;

public enum Status {
    APPLIED("Applied"),
    OA("Online Assessment"),
    INTERVIEW("Interview"),
    SELECTED("Selected"),
    REJECTED("Rejected");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
