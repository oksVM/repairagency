/*
package com.example.repairagency.service;

import com.example.repairagency.dto.AppUserRegistrationDto;
import com.example.repairagency.exception.UserAlreadyExistAuthenticationException;
import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Customer;
import com.example.repairagency.model.Role;
import com.example.repairagency.model.Status;
import com.example.repairagency.repository.AppUserRepository;
import com.example.repairagency.repository.CustomerRepository;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;
    private AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;

@Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, AppUserRepository appUserRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired




    //TODO transaction???
    @Override
    public Customer save(AppUserRegistrationDto appUserRegistrationDto) throws UserAlreadyExistAuthenticationException {

        if (appUserRepository.findByEmail(appUserRegistrationDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistAuthenticationException("There is an account with that email address: "
                    + appUserRegistrationDto.getEmail());
        }
        Customer user = new Customer(appUserRegistrationDto.getFirstName(),
                appUserRegistrationDto.getLastName(),
                appUserRegistrationDto.getEmail(),
                passwordEncoder.encode(appUserRegistrationDto.getPassword()), Role.CUSTOMER, Status.ACTIVE);


        //return customerRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}

*/
