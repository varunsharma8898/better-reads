package com.varun.selfstudy.util;

public enum ImageSize {
    SMALL("S"),
    MEDIUM("M"),
    LARGE("L");

    private final String size;

    ImageSize(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }
}
