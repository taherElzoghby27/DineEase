package com.spring.boot.resturantbackend.utils.enums;

public enum OrderStatus {
    Pending("Pending"), Preparing("Preparing"), Ready("Ready"), Delivered("Delivered");
    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
