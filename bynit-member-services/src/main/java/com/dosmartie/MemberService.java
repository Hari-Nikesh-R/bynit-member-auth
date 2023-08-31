package com.dosmartie;

import com.dosmartie.response.BaseResponse;
import com.dosmartie.response.CustomerResponse;
import com.dosmartie.response.MerchantResponse;
import org.springframework.http.ResponseEntity;

public interface MemberService {
    ResponseEntity<?> getCustomerProfileInfo(String token);
    ResponseEntity<?> getMerchantProfileInfo(String token);
    ResponseEntity<?> companyProfile(String email);
}
