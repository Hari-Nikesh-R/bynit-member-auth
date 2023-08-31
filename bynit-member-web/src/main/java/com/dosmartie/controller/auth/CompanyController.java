package com.dosmartie.controller.auth;

import com.dosmartie.CompanyService;
import com.dosmartie.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;


    @PutMapping(value = "/rating")
    public ResponseEntity<BaseResponse<Object>> rateCompany(@RequestHeader("merchant") String email, @RequestBody Map<String, Double> rating) {
        return companyService.rateCompany(email, rating.get("rate"));
    }
}
