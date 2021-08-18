package com.example.repairagency.service;

import com.example.repairagency.dto.AppUserRegistrationDto;
import com.example.repairagency.exception.UserAlreadyExistAuthenticationException;
import com.example.repairagency.model.*;
import com.example.repairagency.repository.AppUserRepository;
import com.example.repairagency.repository.OrderRepository;
import com.example.repairagency.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AppUserServiceImpl implements AppUserService {

    private AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private ReviewRepository reviewRepository;
    private OrderRepository orderRepository;

    @Autowired
    public AppUserServiceImpl(AppUserRepository appUserRepository, PasswordEncoder passwordEncoder, ReviewRepository reviewRepository, OrderRepository orderRepository) {
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.reviewRepository = reviewRepository;
        this.orderRepository = orderRepository;
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

       return appUserRepository.save(AppUser.builder()
                .firstName(appUserRegistrationDto.getFirstName())
                .lastName(appUserRegistrationDto.getLastName())
                .email(appUserRegistrationDto.getEmail())
                .password(passwordEncoder.encode(appUserRegistrationDto.getPassword()))
                .role(Role.CUSTOMER)
                .build());
    }

    @Override
    public AppUser findById(Long id) throws UsernameNotFoundException{
        return appUserRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(""));
    }

    @Override
    public AppUser updateDeposit(Integer money, Long id) {
        AppUser updatedAppUser = appUserRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(""));
        updatedAppUser.setAmountOfMoney(updatedAppUser.getAmountOfMoney()+money);
        appUserRepository.save(updatedAppUser);
        return updatedAppUser;
    }

    @Override
    public AppUser saveNewMaster(AppUserRegistrationDto appUserRegistrationDto) throws UserAlreadyExistAuthenticationException {
        if (appUserRepository.findByEmail(appUserRegistrationDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistAuthenticationException("There is an account with that email address: "
                    + appUserRegistrationDto.getEmail());
        }

        return appUserRepository.save(AppUser.builder()
                .firstName(appUserRegistrationDto.getFirstName())
                .lastName(appUserRegistrationDto.getLastName())
                .email(appUserRegistrationDto.getEmail())
                .password(passwordEncoder.encode(appUserRegistrationDto.getPassword()))
                .role(Role.MASTER)
                .build());
    }

    @Override
    public Page<AppUser> findAllCustomersPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return appUserRepository.findAllByRole(Role.CUSTOMER, pageable);
    }

    @Override
    public Page<AppUser> findAllMastersPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return appUserRepository.findAllByRole(Role.MASTER, pageable);
    }

    @Override
    public List<AppUser> findAllMasters() {
        return appUserRepository.findAllByRole(Role.MASTER);
    }

    @Override
    public AppUser leaveFeedback(String feedback, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(()->new NoSuchElementException(""));
        Long masterId = order.getMaster().getId();
        AppUser updatedMaster = appUserRepository.findById(masterId).orElseThrow(() -> new UsernameNotFoundException(""));
        Review review = reviewRepository.save(Review.builder()
        .master(updatedMaster)
        .reviewDescription(feedback)
        .build());
        order.setOrderStatus(OrderStatus.REVIEWED);
        orderRepository.save(order);

        return updatedMaster;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(""));
    }
}




