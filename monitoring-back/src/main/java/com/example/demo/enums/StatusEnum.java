package com.example.demo.enums;

public enum StatusEnum {
    OK("OK"),
    WARNING("WARNING"),
    CRITICAL("CRITICAL");

    private String value;

    StatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
