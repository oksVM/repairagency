/*
package com.example.repairagency.auth;

import com.example.repairagency.model.Role;
import com.google.common.collect.Lists;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository("fake")
public class ApplicationUserDaoService implements ApplicationUserDao{

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationUserDaoService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers(){
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
              new ApplicationUser("Anna", passwordEncoder.encode("Anna"),
                      Role.CUSTOMER.getAuthorities(),
                      true,true,true,true),
               new ApplicationUser("Sara", passwordEncoder.encode("Sara"),
                Role.ADMIN.getAuthorities(),
                true,true,true,true)
                );
        return applicationUsers;
    }
}
*/
