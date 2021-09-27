package com.example.repairagency.service;

import com.example.repairagency.dto.PriceDto;
import com.example.repairagency.exception.NotEnoughMoneyException;
import com.example.repairagency.model.Order;
import org.springframework.data.domain.Page;

import java.sql.SQLException;

public interface OrderService {

    Order save(Order order);
    Page<Order> findAllCurrentCustomerOrders(int pageNo, int pageSize);
    Page<Order> findAllOrdersPaginated(String keyWord, int pageNo, int pageSize, String sortField, String sortDirection);
    Order findOrderById(Long id);
    Order setPrice(PriceDto price, Long id);
    Order payForOrder(Long id) throws NotEnoughMoneyException, SQLException;
    Order setMaster(Long masterId, Long id);
    Page<Order> findAllCurrentMasterOrders(int pageNo, int pageSize);
    Order takeInWork(Long id);
    Order markAsDone(Long id);
}

