package com.example.repairagency.service;

import com.example.repairagency.model.Review;

import java.util.List;

public interface ReviewService {
    public List<Review> findAllByMasterId(Long id);
}
