package com.example.repairagency.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Customer extends AppUser {

    @Column(name = "amount_of_money")
    private int amountOfMoney;
  @Column(name = "order_set")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<Order> orderSet;

    public Customer(String firstName, String lastName, String email, String password, Role role, Status status, int amountOfMoney/*, Set<Order> orderSet*/) {
        super(firstName, lastName, email, password, role, status);
        this.amountOfMoney = amountOfMoney;
        //this.orderSet = orderSet;
    }
}
