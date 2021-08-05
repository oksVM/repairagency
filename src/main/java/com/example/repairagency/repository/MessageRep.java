package com.example.repairagency.repository;

import com.example.repairagency.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRep extends JpaRepository<Message, Integer> {
}
