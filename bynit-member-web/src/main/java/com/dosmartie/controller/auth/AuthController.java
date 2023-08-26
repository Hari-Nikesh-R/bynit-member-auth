package com.dosmartie.controller.auth;

import com.dosmartie.CustomerRegistrationService;
import com.dosmartie.JwtTokenUtil;
import com.dosmartie.UserService;
import com.dosmartie.request.AuthRequest;
import com.dosmartie.request.UserRequest;
import com.dosmartie.response.AuthResponse;
import com.dosmartie.response.BaseResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.impl.DefaultClaims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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

    @PostMapping(value = "/register")
    public ResponseEntity<BaseResponse<Object>> registerUser(@RequestBody UserRequest userInfoDto) {
        return userRegisterService.registerNewUser(userInfoDto,"");
    }

    @GetMapping(value = "/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) throws Exception {
        return userService.getRefreshToken(request);
    }
}
