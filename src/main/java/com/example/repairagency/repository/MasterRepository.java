
package com.example.repairagency.repository;

import com.example.repairagency.model.Customer;
import com.example.repairagency.model.Master;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {
    Optional<Master> findByEmail(String email);
}

