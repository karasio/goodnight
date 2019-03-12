package com.example.goodnight;

public class Tip {
    private final String NAME;
    private final String DESCRIPTION;

    public Tip (String name, String description) {
        this.NAME = name;
        this.DESCRIPTION = description;
    }

    public String getName() {
        return this.NAME;
    }

    public String getDescription() {
        return this.DESCRIPTION;
    }

    @Override
    public String toString() {
        return this.NAME;
    }
}
