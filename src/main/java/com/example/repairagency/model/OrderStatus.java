package com.example.repairagency.model;

import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;

public enum OrderStatus {
    WAIT_FOR_ADMIN_CONFIRMATION("WAIT_FOR_ADMIN_CONFIRMATION"),
    WAIT_FOR_PAYMENT ("WAIT_FOR_PAYMENT"),
    PAID("PAID"),
    WAIT_FOR_MASTER_CONFIRMATION("WAIT_FOR_MASTER_CONFIRMATION"),
    CANCELED("CANCELED"),
    IN_WORK("IN_WORK"),
    DONE("DONE"),
    REVIEWED("REVIEWED");

    private String orderStatusDescription;

    OrderStatus(String orderStatusDescription) {
        this.orderStatusDescription = orderStatusDescription;
    }

    public String getOrderStatusDescription() {
        return orderStatusDescription;
    }

    public void setOrderStatusDescription(String orderStatusDescription) {
        this.orderStatusDescription = orderStatusDescription;
    }

    @Override
    public String toString() {
        return orderStatusDescription;
    }
}
