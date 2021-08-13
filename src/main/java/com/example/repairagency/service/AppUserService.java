package com.example.repairagency.service;

import com.example.repairagency.dto.AppUserRegistrationDto;
import com.example.repairagency.exception.UserAlreadyExistAuthenticationException;
import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Role;
import org.checkerframework.checker.units.qual.A;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AppUserService extends UserDetailsService {

    List<AppUser> findAllCustomers();
    AppUser saveNewCustomer(AppUserRegistrationDto appUserRegistrationDto) throws UserAlreadyExistAuthenticationException;

    AppUser updateDeposit(Integer money);

    AppUser saveNewMaster(AppUserRegistrationDto appUserRegistrationDto) throws UserAlreadyExistAuthenticationException;
}
