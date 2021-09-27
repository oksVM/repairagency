package com.example.repairagency.service;

import com.example.repairagency.model.Review;
import org.springframework.data.domain.Page;


public interface ReviewService {

   Page<Review> findAllReviewsByMasterId(Long id, int pageNo, int pageSize);
   Review leaveFeedback(String feedback, Long orderId);
}
