package com.dosmartie;

import com.dosmartie.response.BaseResponse;
import com.dosmartie.response.MerchantResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
    ResponseEntity<BaseResponse<Object>> verifyMerchant(String email);
    ResponseEntity<BaseResponse<List<MerchantResponse>>> getNonVerifyMerchant();
}
