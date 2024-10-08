package org.example.shopapp.common;

public enum Role {
    ADMIN("Admin"),
    USER("User");

    private final String text;

    private Role(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
