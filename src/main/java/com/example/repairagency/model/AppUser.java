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
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    //@Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    //@Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
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


