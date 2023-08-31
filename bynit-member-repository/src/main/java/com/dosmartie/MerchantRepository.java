package com.dosmartie;

import com.dosmartie.entity.MerchantInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<MerchantInfo, Integer> {
    Optional<MerchantInfo> findByEmail(String email);
    @Query(value = "select * from merchant_info where is_verified = false and role = 'MERCHANT'", nativeQuery = true)
    List<MerchantInfo> findByNonVerified();
}
