package com.example.repairagency.repository;

import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
Optional <Order> findById(Long id);
//@Query("SELECT o FROM Order o  WHERE o.master.email LIKE %?1%")
@Query("SELECT o FROM Order o WHERE LOWER(o.orderStatus) LIKE LOWER(CONCAT('%', ?1,'%'))")
Page<Order> findAll(@Param("keyword") String keyword, Pageable pageable);
Page<Order> findAllByCustomerId(Long id, Pageable pageable);
}
