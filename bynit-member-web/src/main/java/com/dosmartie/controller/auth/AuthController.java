package com.dosmartie.controller.auth;

import com.dosmartie.CustomerRegistrationService;
import com.dosmartie.UserService;
import com.dosmartie.request.AuthRequest;
import com.dosmartie.request.CustomerRequest;
import com.dosmartie.request.MerchantRequest;
import com.dosmartie.response.AuthResponse;
import com.dosmartie.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/oauth")
public class AuthController {

    @Autowired
    private CustomerRegistrationService userRegisterService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/sign-in")
    public ResponseEntity<BaseResponse<AuthResponse>> authenticate(@RequestBody AuthRequest authRequest) {
        return userService.signInUser(authRequest);
    }

//    @PostMapping(value = "/logout")
//    public ResponseEntity<BaseResponse<AuthResponse>> logout(@RequestHeader(AUTHORIZATION) String token) {
//        return userService.logout(token);
//    }

    @PostMapping(value = "/register/user")
    public ResponseEntity<BaseResponse<Object>> registerUser(@RequestBody CustomerRequest userInfoDto) {
        return userRegisterService.registerNewUser(userInfoDto, "");
    }

    @PostMapping(value = "/register/merchant")
    public ResponseEntity<BaseResponse<Object>> registerUser(@RequestBody MerchantRequest userInfoDto) {
        return userRegisterService.registerNewUser(userInfoDto, "");
    }

    @GetMapping(value = "/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) throws Exception {
        return userService.getRefreshToken(request);
    }
}
