package com.example.repairagency.service;


import com.example.repairagency.dto.PriceDto;
import com.example.repairagency.exception.NotEnoughMoneyException;
import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Order;
import com.example.repairagency.model.OrderStatus;
import com.example.repairagency.repository.AppUserRepository;
import com.example.repairagency.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.util.NoSuchElementException;

/**
 * Service for handling all manipulations  with order
 */
@Service
@Slf4j
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

    /**
     * method for creating new order by appUser
     * @param order
     * @return
     */
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

    /**
     * method for obtaining orders for current customer
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<Order> findAllCurrentCustomerOrders(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return orderRepository
                .findAllByCustomerId(((AppUser) appUserService
                        .loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName())).getId(), pageable);
    }

    /**
     * method for obtaining all orders, that are in the repair agency
     * @param keyWord
     * @param pageNo
     * @param pageSize
     * @param sortField
     * @param sortDirection
     * @return
     */
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

    @Override
    public Order findOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
    }

    /**
     * method for setting order price by admin
     * @param price
     * @param id
     * @return
     */
   @Override
   @Transactional
   public Order setPrice(PriceDto price, Long id) {
       Order order = findOrderById(id);
       order.setPrice(price.getAmountOfMoney());
       order.setOrderStatus(OrderStatus.WAIT_FOR_PAYMENT);
       return order;
   }

    /**
     * method for order payment by customer
     * @param id
     * @return
     * @throws NotEnoughMoneyException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,
            isolation = Isolation.SERIALIZABLE, rollbackFor = {NotEnoughMoneyException.class})
    public Order payForOrder(Long id) throws NotEnoughMoneyException {
        Order order = findOrderById(id);
        AppUser currentAppUser = ((AppUser) appUserService
                .loadUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        if(order.getPrice()>currentAppUser.getAmountOfMoney()){
            throw new NotEnoughMoneyException();
        }
        currentAppUser.setAmountOfMoney(currentAppUser.getAmountOfMoney() - order.getPrice());
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
