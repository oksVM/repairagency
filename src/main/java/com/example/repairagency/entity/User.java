package com.example.repairagency.entity;

import com.example.repairagency.model.Role;
import com.example.repairagency.model.Status;
import lombok.*;

import javax.persistence.*;


@Data
@Entity
@Table(name="users")
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;



    @Column(name = "email")
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String secondName;
    @Column(name = "password")
    @Enumerated(value = EnumType.STRING)
    private String password;
    @Column(name = "role")
    private Role role;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public User() {

    }
}
