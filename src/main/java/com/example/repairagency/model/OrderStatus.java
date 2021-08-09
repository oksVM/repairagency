package com.example.repairagency.model;

import lombok.Data;
import org.springframework.security.access.prepost.PreAuthorize;

public enum OrderStatus {
    WAIT_FOR_ADMIN_CONFIRMATION("Order wait for administrator confirmation"),
    WAIT_FOR_PAYMENT ("Order wait for waiment"),
    PAID("Order is paid"),
    CANCELED("Order is canceled"),
    IN_WORK("Order in work"),
    DONE("Order is done");

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
}
