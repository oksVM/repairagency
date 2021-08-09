package com.example.repairagency.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class Adm extends AppUser {

   String entity;
   int money;

   public Adm(String firstName, String lastName, String email, String password, Role role, Status status, String entity, int money) {
      super(firstName, lastName, email, password, role, status);
      this.entity = entity;
      this.money = money;
   }
}
