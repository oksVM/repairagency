package com.example.repairagency.service;

import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Order;
import com.example.repairagency.model.OrderStatus;
import com.example.repairagency.model.Review;
import com.example.repairagency.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class ReviewServiceImpl implements ReviewService{

    private ReviewRepository reviewRepository;
    private OrderService orderService;
    private AppUserService appUserService;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, OrderService orderService, AppUserService appUserService) {
        this.reviewRepository = reviewRepository;
        this.orderService = orderService;
        this.appUserService = appUserService;
    }


    @Override
    public Page<Review> findAllReviewsByMasterId(Long id,int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return reviewRepository.findAllByMasterId(id, pageable);
    }

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
