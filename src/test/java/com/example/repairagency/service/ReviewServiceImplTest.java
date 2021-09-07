package com.example.repairagency.service;

import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Order;
import com.example.repairagency.model.Review;
import com.example.repairagency.model.Role;
import com.example.repairagency.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.Arrays;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    ReviewRepository reviewRepository;
    @Mock
    OrderService orderService;
    @Mock
    AppUserService appUserService;

    @InjectMocks
    ReviewServiceImpl reviewService;

    @Test
    void findAllReviewsByMasterId() {
        AppUser master = AppUser.builder()
                .id(1L)
                .role(Role.MASTER)
                .build();
        Review review1 = Review.builder()
                .id(1L)
                .master(master)
                .build();
        Review review2 = Review.builder()
                .id(2L)
                .master(master)
                .build();


        Page<Review> page = new PageImpl<>(Arrays.asList(review1,review2));
        Pageable pageable = PageRequest.of(0, 2);
        Mockito.when(reviewRepository.findAllByMasterId(1L,pageable)).thenReturn(page);
        assertThat(reviewService.findAllReviewsByMasterId(1L,1,2)).isEqualTo(page);
    }

    @Test
    void leaveFeedback() {
        AppUser master = AppUser.builder()
                .id(1L)
                .role(Role.MASTER)
                .build();
        Order order = Order.builder()
                .id(1L)
                .master(master)
                .build();

        Review review1 = Review.builder()
                .master(master)
                .reviewDescription("good work")
                .build();

        Mockito.when(reviewRepository.save(any(Review.class))).thenReturn(review1);
        Mockito.when(orderService.findOrderById(1L)).thenReturn(order);
        assertThat(reviewService.leaveFeedback("good work",1L)).isEqualTo(review1);
    }
}