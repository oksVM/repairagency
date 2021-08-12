package com.example.repairagency.service;

import com.example.repairagency.dto.AppUserRegistrationDto;
import com.example.repairagency.exception.UserAlreadyExistAuthenticationException;
import com.example.repairagency.model.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {
   AppUser save(AppUserRegistrationDto appUserRegistrationDto) throws UserAlreadyExistAuthenticationException;

   AppUser updateDeposit(Integer money);
}
