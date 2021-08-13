package com.example.repairagency.service;

import com.example.repairagency.dto.AppUserRegistrationDto;
import com.example.repairagency.exception.UserAlreadyExistAuthenticationException;
import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Role;
import org.checkerframework.checker.units.qual.A;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface AppUserService extends UserDetailsService {

    List<AppUser> findAllCustomers();
    AppUser saveNewCustomer(AppUserRegistrationDto appUserRegistrationDto) throws UserAlreadyExistAuthenticationException;
    Optional<AppUser> findById(Long id);
    AppUser updateDeposit(Integer money, Long id);

    AppUser saveNewMaster(AppUserRegistrationDto appUserRegistrationDto) throws UserAlreadyExistAuthenticationException;
}
