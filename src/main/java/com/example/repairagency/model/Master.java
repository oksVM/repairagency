package com.example.repairagency.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Master extends AppUser {
    //@OneToMany(cascade = CascadeType.ALL, mappedBy = "master")
    //private Set<Order> orderSet;
}
