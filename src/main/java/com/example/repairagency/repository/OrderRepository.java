package com.example.repairagency.repository;

import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
//@Query("SELECT o FROM orders o WHERE o.orderStatus LIKE %?1%")
//List<Order> findAll(String keyWord);
@Query("SELECT o FROM Order o  WHERE o.orderName LIKE %?1%")
Page<Order> findAll(String keyWord, Pageable pageable);
    Page<Order> findAllByCustomerId(Long id, Pageable pageable);
}
