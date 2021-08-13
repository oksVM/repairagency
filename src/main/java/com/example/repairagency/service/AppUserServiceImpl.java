package com.example.repairagency.service;

import com.example.repairagency.dto.AppUserRegistrationDto;
import com.example.repairagency.exception.UserAlreadyExistAuthenticationException;
import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Role;
import com.example.repairagency.repository.AppUserRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserServiceImpl implements AppUserService {

    private AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<AppUser> findAllCustomers() {
        return appUserRepository.findAllByRole(Role.CUSTOMER);
    }

    @Override
    public AppUser saveNewCustomer(AppUserRegistrationDto appUserRegistrationDto) throws UserAlreadyExistAuthenticationException {

        if (appUserRepository.findByEmail(appUserRegistrationDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistAuthenticationException("There is an account with that email address: "
                    + appUserRegistrationDto.getEmail());
        }

        //TODO
       return appUserRepository.save(AppUser.builder()
                .firstName(appUserRegistrationDto.getFirstName())
                .lastName(appUserRegistrationDto.getLastName())
                .email(appUserRegistrationDto.getEmail())
                .password(passwordEncoder.encode(appUserRegistrationDto.getPassword()))
                .role(Role.CUSTOMER)
                .build());

     /*AppUser user = new AppUser(appUserRegistrationDto.getFirstName(),
                appUserRegistrationDto.getLastName(),
                appUserRegistrationDto.getEmail(),
                passwordEncoder.encode(appUserRegistrationDto.getPassword()), Role.CUSTOMER);

        return appUserRepository.save(user);*/
    }

    @Override
    public AppUser saveNewMaster(AppUserRegistrationDto appUserRegistrationDto) throws UserAlreadyExistAuthenticationException {
        if (appUserRepository.findByEmail(appUserRegistrationDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistAuthenticationException("There is an account with that email address: "
                    + appUserRegistrationDto.getEmail());
        }

        //TODO
        return appUserRepository.save(AppUser.builder()
                .firstName(appUserRegistrationDto.getFirstName())
                .lastName(appUserRegistrationDto.getLastName())
                .email(appUserRegistrationDto.getEmail())
                .password(passwordEncoder.encode(appUserRegistrationDto.getPassword()))
                .role(Role.MASTER)
                .build());
    }

    @Override
    public AppUser updateDeposit(Integer money) {
        AppUser updatedAppUser = (AppUser) loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        updatedAppUser.setAmountOfMoney(updatedAppUser.getAmountOfMoney()+money);
        appUserRepository.save(updatedAppUser);
        return updatedAppUser;
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(""));
    }
}




