package com.example.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TaskStatus {
    PENDING("pending"),
    IN_PROGRESS("in_progress"),
    COMPLETED("completed");

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

    @JsonCreator
    public static TaskStatus from(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Status value cannot be null");
        }

        for (TaskStatus status : values()) {
            if (status.value.equalsIgnoreCase(value.strip())) {
                return status;
            }
        }

        throw new IllegalArgumentException("Invalid status: " + value);
    }
}
