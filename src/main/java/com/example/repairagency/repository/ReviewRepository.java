package com.example.repairagency.repository;

import com.example.repairagency.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

     List<Review> findAllByMasterId(Long masterId);
     Page<Review> findAllByMasterId(Long id, Pageable pageable);
}
