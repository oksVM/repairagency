package com.example.repairagency.repository;

import com.example.repairagency.model.AppUser;
import com.example.repairagency.model.Review;
import com.example.repairagency.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}
