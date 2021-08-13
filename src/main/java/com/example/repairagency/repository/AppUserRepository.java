package com.example.repairagency.repository;

//import com.example.repairagency.model.User;
import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    //AppUser findByEmail(String email);

    List<AppUser> findAllByRole(Role role);

    Optional <AppUser> findByEmail(String email);
}
