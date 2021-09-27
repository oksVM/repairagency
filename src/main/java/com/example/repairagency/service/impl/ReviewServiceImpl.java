package com.example.repairagency.service.impl;

import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Order;
import com.example.repairagency.model.OrderStatus;
import com.example.repairagency.model.Review;
import com.example.repairagency.repository.ReviewRepository;
import com.example.repairagency.service.AppUserService;
import com.example.repairagency.service.OrderService;
import com.example.repairagency.service.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for handling all manipulations  with reviews
 */
@Service
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderService orderService;
    private final AppUserService appUserService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, OrderService orderService, AppUserService appUserService) {
        this.reviewRepository = reviewRepository;
        this.orderService = orderService;
        this.appUserService = appUserService;
    }

    /**
     * methods for obtaining reviews by master
     * @param id
     * @param pageNo
     * @param pageSize
     * @return reviews per page
     */
    @Override
    public Page<Review> findAllReviewsByMasterId(Long id,int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return reviewRepository.findAllByMasterId(id, pageable);
    }

    /**
     * methods for saving review
     * @param feedback
     * @param orderId
     * @return saved Review
     */
    @Override
    @Transactional
    public Review leaveFeedback(String feedback, Long orderId) {
        Order order = orderService.findOrderById(orderId);
        Long masterId = order.getMaster().getId();
        AppUser updatedMaster = appUserService.findById(masterId);
        Review review = reviewRepository.save(Review.builder()
                .master(updatedMaster)
                .reviewDescription(feedback)
                .build());
        order.setOrderStatus(OrderStatus.REVIEWED);
        orderService.save(order);

        return review;
    }
}
