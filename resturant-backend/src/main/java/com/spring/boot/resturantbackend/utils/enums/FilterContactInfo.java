package com.spring.boot.resturantbackend.utils.enums;

public enum FilterContactInfo {
    NOT_REPLIED("NOT_REPLIED"), REPLIED("REPLIED");
    private String status;

    FilterContactInfo(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
