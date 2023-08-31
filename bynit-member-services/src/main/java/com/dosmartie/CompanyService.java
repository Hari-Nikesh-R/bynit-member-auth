package com.dosmartie;

import com.dosmartie.request.CompanyRequest;
import com.dosmartie.response.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface CompanyService {
    ResponseEntity<BaseResponse<Object>> rateCompany(String email, double rating);
}
