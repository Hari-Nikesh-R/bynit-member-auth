package com.dosmartie;

import com.dosmartie.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService{
    @Override
    public ResponseEntity<BaseResponse<Object>> rateCompany(String email, double rating) {
        return null;
    }
}
