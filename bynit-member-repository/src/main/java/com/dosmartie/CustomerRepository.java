package com.dosmartie;

import com.dosmartie.entity.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerInfo, Integer> {
    Optional<CustomerInfo> findByEmail(String email);
}
