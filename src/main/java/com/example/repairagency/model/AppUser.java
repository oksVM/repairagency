package com.example.repairagency.model;

import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
 public class AppUser implements UserDetails {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name="app_user_id",nullable = false)
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
    @Column(name = "role", nullable = false)
    private Role role;
    @Column(name = "amount_of_money")
    private Integer amountOfMoney;
    @Column(name = "professional_area")
    private Area professionalArea;
    @Column(name = "score")
    private Double score;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<Order> customerOrders;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "master")
    private Set<Order> masterOrders;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "master")
    private Set<Review> reviews;

   public AppUser(String firstName, String lastName, String email, String password, Role role) {
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.password = password;
      this.role = role;
   }

    public Long getId() {
        return id;
    }

    @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return role.getAuthorities();
   }

   @Override
   public String getUsername() {
      return email;
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }
}


