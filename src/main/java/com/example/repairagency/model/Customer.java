package com.example.repairagency.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import javax.persistence.Entity;

@Entity
@Data
public class Customer extends AppUser {


    long amountOfMoney;

    public Customer(String firstName, String lastName, String email, String password, Role role, Status status, long amountOfMoney) {
        super(firstName, lastName, email, password, role, status);
        this.amountOfMoney = amountOfMoney;
    }

    public Customer() {
        super();
    }
}
