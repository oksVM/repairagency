package com.example.repairagency.service;

import com.example.repairagency.dto.AppUserRegistrationDto;
import com.example.repairagency.dto.DepositDTO;
import com.example.repairagency.exception.UserAlreadyExistAuthenticationException;
import com.example.repairagency.model.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AppUserService extends UserDetailsService {

    AppUser saveNewCustomer(AppUserRegistrationDto appUserRegistrationDto) throws UserAlreadyExistAuthenticationException;
    AppUser findById(Long id);
    AppUser updateDeposit(DepositDTO money, Long id);
    AppUser saveNewMaster(AppUserRegistrationDto appUserRegistrationDto) throws UserAlreadyExistAuthenticationException;
    Page<AppUser> findAllCustomersPaginated(int pageNo, int pageSize);
    Page<AppUser> findAllMastersPaginated(int pageNo, int pageSize);
    List<AppUser> findAllMasters();
    AppUser leaveFeedback(String feedback, Long orderId);
}
