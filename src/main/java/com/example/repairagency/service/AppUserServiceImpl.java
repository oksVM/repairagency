package com.example.repairagency.service;

import com.example.repairagency.dto.AppUserRegistrationDto;
import com.example.repairagency.exception.UserAlreadyExistAuthenticationException;
import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Role;
import com.example.repairagency.model.Status;
import com.example.repairagency.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService{


    private AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }


    //TODO transaction???
    @Override
    public AppUser save(AppUserRegistrationDto appUserRegistrationDto) throws UserAlreadyExistAuthenticationException {

            if (appUserRepository.findByEmail(appUserRegistrationDto.getEmail()).isPresent()) {
                throw new UserAlreadyExistAuthenticationException("There is an account with that email address: "
                        + appUserRegistrationDto.getEmail());
            }
        AppUser user = new AppUser(appUserRegistrationDto.getFirstName(),
                appUserRegistrationDto.getLastName(),
                appUserRegistrationDto.getEmail(),
                passwordEncoder.encode(appUserRegistrationDto.getPassword()), Role.CUSTOMER, Status.ACTIVE);

        return appUserRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(""));
    }


    }




