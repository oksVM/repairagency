package com.example.repairagency.service;

import com.example.repairagency.dto.AppUserRegistrationDto;
import com.example.repairagency.dto.PriceDto;
import com.example.repairagency.exception.NotEnoughMoneyException;
import com.example.repairagency.exception.UserAlreadyExistAuthenticationException;
import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Order;
import com.example.repairagency.model.OrderStatus;
import com.example.repairagency.model.Role;
import com.example.repairagency.repository.AppUserRepository;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderServiceImpl implements OrderService{

    private OrderRepository orderRepository;
    private AppUserService appUserService;
    private AppUserRepository appUserRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, AppUserService appUserService, AppUserRepository appUserRepository) {
        this.orderRepository = orderRepository;
        this.appUserService = appUserService;
        this.appUserRepository = appUserRepository;
    }


    public Order save(Order order) {
        return orderRepository.save(Order.builder()
                .orderName(order.getOrderName())
                .orderDescription(order.getOrderDescription())
                .area(order.getArea())
                .orderStatus(OrderStatus.WAIT_FOR_ADMIN_CONFIRMATION)
                .offsetDateTime(OffsetDateTime.now())
                .customer((AppUser) appUserService.loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()))
                .build());
    }


    @Override
    public Page<Order> findAllCurrentCustomerOrders(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return orderRepository
                .findAllByCustomerId(((AppUser) appUserService
                        .loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName())).getId(), pageable);
    }

    @Override
    public Page<Order> findAllOrdersPaginated(String keyWord, int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortField):
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        //TODO
        if(keyWord!=null){
            return orderRepository.findAll(keyWord,pageable);
        }
        return orderRepository.findAll(pageable);
    }

    @Override
    public Order findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
    }

   @Override
   @Transactional
   public Order setPrice(PriceDto price, Long id) {
       Order order = findOrderById(id);
       order.setPrice(price.getAmountOfMoney());
       order.setOrderStatus(OrderStatus.WAIT_FOR_PAYMENT);
       return order;
   }

    @Override
    @Transactional
    public Order payForOrder(Long id) throws NotEnoughMoneyException {
        Order order = findOrderById(id);
        AppUser currentAppUser = ((AppUser) appUserService
                .loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        if(order.getPrice()>currentAppUser.getAmountOfMoney()){
            throw new NotEnoughMoneyException("not enought money");
        }
        currentAppUser.setAmountOfMoney(currentAppUser.getAmountOfMoney()-order.getPrice());
        appUserRepository.save(currentAppUser);
        order.setOrderStatus(OrderStatus.PAID);
        orderRepository.save(order);
        return order;
    }

    @Override
    @Transactional
    public Order setMaster(Long masterId, Long id) {
        Order order = findOrderById(id);
        AppUser master = appUserService.findById(masterId);
        order.setMaster(master);
        order.setOrderStatus(OrderStatus.WAIT_FOR_MASTER_CONFIRMATION);
        orderRepository.save(order);
        return order;
    }

    @Override
    public Page<Order> findAllCurrentMasterOrders(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return orderRepository
                .findAllByMasterId(((AppUser) appUserService
                        .loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName())).getId(), pageable);
    }

    @Override
    @Transactional
    public Order takeInWork(Long id) {
        Order order = findOrderById(id);
        order.setOrderStatus(OrderStatus.IN_WORK);
        orderRepository.save(order);
        return order;
    }

    @Override
    @Transactional
    public Order markAsDone(Long id) {
        Order order = findOrderById(id);
        order.setOrderStatus(OrderStatus.DONE);
        orderRepository.save(order);
        return order;
    }
}
