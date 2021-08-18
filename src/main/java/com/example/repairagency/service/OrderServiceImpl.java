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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    private OrderRepository orderRepository;
    private AppUserService appUserService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, AppUserService appUserService) {
        this.orderRepository = orderRepository;
        this.appUserService = appUserService;
    }

    public Order save(Order order) {
        order.setOrderStatus(OrderStatus.WAIT_FOR_ADMIN_CONFIRMATION);
        //TODO DATE
        order.setOffsetDateTime(OffsetDateTime.now());
        order.setCustomer((AppUser) appUserService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        return this.orderRepository.save(order);
    }

    @Override
    public Page<Order> findAllCurrentCustomerOrders(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return orderRepository
                .findAllByCustomerId(((AppUser) appUserService
                        .loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName())).getId(), pageable);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Page<Order> findAllOrdersPaginated(String keyWord, int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortField):
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        if(keyWord!=null){
            return orderRepository.findAll(keyWord,pageable);
        }
        return orderRepository.findAll(pageable);
    }
}
