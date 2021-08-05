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
    private Long id;
    private String firstname;
    private String lastname;
    @Column(unique = true)
    private String email;
    private String password;
    private Role role;
    private Status status;

   public AppUser(String firstname, String lastname, String email, String password, Role role, Status status) {
      this.firstname = firstname;
      this.lastname = lastname;
      this.email = email;
      this.password = password;
      this.role = role;
      this.status = status;
   }
}