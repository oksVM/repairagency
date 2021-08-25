package com.example.repairagency.service;

import com.example.repairagency.model.Order;
import com.example.repairagency.model.Review;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewService {
   // List<Review> findAllByMasterId(Long id);
    Page<Review> findAllReviewsByMasterId(Long id, int pageNo, int pageSize);
}
