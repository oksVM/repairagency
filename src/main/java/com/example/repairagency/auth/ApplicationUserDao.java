package com.example.repairagency.auth;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public interface ApplicationUserDao {

    Optional<ApplicationUser> selectApplicationUserByUsername(String username);
}
