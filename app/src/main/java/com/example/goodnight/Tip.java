package com.example.goodnight;

public class Tip {
    private String name;
    private String description;

    public Tip (String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
