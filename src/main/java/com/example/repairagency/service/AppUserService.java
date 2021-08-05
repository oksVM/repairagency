package com.example.repairagency.service;

import com.example.repairagency.dto.AppUserRegistrationDto;
import com.example.repairagency.model.AppUser;

public interface AppUserService {
    AppUser save(AppUserRegistrationDto appUserRegistrationDto);
}
