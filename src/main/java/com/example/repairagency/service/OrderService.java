package com.example.repairagency.service;

import com.example.repairagency.exception.NotEnoughMoneyException;
import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Order;
import com.example.repairagency.model.OrderStatus;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.OffsetDateTime;
import java.util.List;

public interface OrderService {

    Order save(Order order);

    Page<Order> findAllCurrentCustomerOrders(int pageNo, int pageSize);

    List<Order> findAllOrders();
    Page<Order> findAllOrdersPaginated(String keyWord, int pageNo, int pageSize, String sortField, String sortDirection);


    Order findOrderById(Long id);

    Order setPrice(Integer price, Long id);

    Order payForOrder(Long id) throws NotEnoughMoneyException;
}

