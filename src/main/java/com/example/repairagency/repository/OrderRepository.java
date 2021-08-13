package com.example.repairagency.repository;

import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Override
    List<Order> findAll();

    List<Order> findAllByCustomerId(Long id);
}
