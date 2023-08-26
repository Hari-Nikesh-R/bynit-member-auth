package com.dosmartie.controller.auth;

import com.dosmartie.CustomerRegistrationService;
import com.dosmartie.request.UserRequest;
import com.dosmartie.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@PreAuthorize("hasAuthority('SUPER_ADMIN')")
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private CustomerRegistrationService userRegisterService;

    @PostMapping(value = "/register")
    public ResponseEntity<BaseResponse<Object>> registerAdmin(@RequestBody UserRequest userRequest, @RequestHeader(AUTHORIZATION) String token) {
        return userRegisterService.registerNewUser(userRequest, token);
    }
}
