package com.example.repairagency.entity;

import lombok.*;

@Data
@AllArgsConstructor
public class Customer {

    private Long customerId;
    private String firstName;
    private String secondName;
}
