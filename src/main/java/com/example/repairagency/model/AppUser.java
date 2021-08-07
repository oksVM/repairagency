package com.example.repairagency.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Data
@NoArgsConstructor
 public class AppUser {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Status status;

   public AppUser(String firstName, String lastName, String email, String password, Role role, Status status) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.password = password;
      this.role = role;
      this.status = status;
   }
}


