package com.spring.boot.resturantbackend.utils.enums;

public enum FilterContactInfo {
    Replied("Replied"), NotReplied("NotReplied"), All("All");
    private String status;

    FilterContactInfo(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
