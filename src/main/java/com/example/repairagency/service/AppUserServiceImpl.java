package com.example.repairagency.service;

import com.example.repairagency.dto.AppUserRegistrationDto;
import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Role;
import com.example.repairagency.model.Status;
import com.example.repairagency.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserServiceImpl implements AppUserService{


    private AppUserRepository appUserRepository;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }



    @Override
    public AppUser save(AppUserRegistrationDto appUserRegistrationDto) {
        AppUser user = new AppUser(appUserRegistrationDto.getFirstname(),
                appUserRegistrationDto.getLastname(),
                appUserRegistrationDto.getEmail(),
                appUserRegistrationDto.getPassword(), Role.CUSTOMER, Status.ACTIVE);
        return appUserRepository.save(user);
    }
}
