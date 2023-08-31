package com.dosmartie.controller.auth;

import com.dosmartie.AdminService;
import com.dosmartie.CustomerRegistrationService;
import com.dosmartie.request.UserRequest;
import com.dosmartie.response.BaseResponse;
import com.dosmartie.response.MerchantResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@CrossOrigin

@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private CustomerRegistrationService userRegisterService;

    @Autowired
    private AdminService adminService;

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping(value = "/register")
    public ResponseEntity<BaseResponse<Object>> registerAdmin(@RequestBody UserRequest userRequest, @RequestHeader(AUTHORIZATION) String token) {
        return userRegisterService.registerNewUser(userRequest, token);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/verify-merchant")
    public ResponseEntity<BaseResponse<Object>> verifyMerchant(@RequestParam("merchantEmail") String email) {
        return adminService.verifyMerchant(email);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/verify-merchant")
    public ResponseEntity<BaseResponse<List<MerchantResponse>>> getNonVerifyMerchant() {
        return adminService.getNonVerifyMerchant();
    }
}
