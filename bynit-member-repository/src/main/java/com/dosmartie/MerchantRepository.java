package com.dosmartie;

import com.dosmartie.entity.MerchantInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<MerchantInfo, Integer> {
    Optional<MerchantInfo> findByEmail(String email);
}
