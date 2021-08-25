package com.example.repairagency.service;

import com.example.repairagency.model.Review;
import com.example.repairagency.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService{

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> findAllByMasterId(Long id) {
        return reviewRepository.findAllByMasterId(id);
    }
}
