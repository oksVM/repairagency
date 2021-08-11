package com.example.repairagency.service;

import com.example.repairagency.dto.AppUserRegistrationDto;
import com.example.repairagency.exception.UserAlreadyExistAuthenticationException;
import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Order;
import com.example.repairagency.model.OrderStatus;
import com.example.repairagency.model.Role;
import com.example.repairagency.repository.OrderRepository;
import javassist.NotFoundException;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.OffsetDateTime;

@Service
public class OrderService {

private OrderRepository orderRepository;
private AppUserService appUserService;

@Autowired
    public OrderService(OrderRepository orderRepository, AppUserService appUserService) {
        this.orderRepository = orderRepository;
        this.appUserService = appUserService;
    }

    public Order save(Order order) {
order.setOrderStatus(OrderStatus.WAIT_FOR_ADMIN_CONFIRMATION);
order.setOffsetDateTime(OffsetDateTime.now());
order.setCustomer((AppUser) appUserService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        return this.orderRepository.save(order);
    }





}
