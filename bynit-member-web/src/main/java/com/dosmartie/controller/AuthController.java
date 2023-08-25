package com.dosmartie.controller;

import com.dosmartie.CustomerRegistrationService;
import com.dosmartie.JwtTokenUtil;
import com.dosmartie.request.UserRequest;
import com.dosmartie.request.AuthRequest;
import com.dosmartie.response.AuthResponse;
import com.dosmartie.response.BaseResponse;
import io.jsonwebtoken.impl.DefaultClaims;
import jakarta.servlet.http.HttpServletRequest;
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
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private CustomerRegistrationService userRegisterService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping(value = "/sign-in")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        System.out.println("Email" + authRequest.getEmail());
        authenticate(authRequest.getEmail(), authRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<BaseResponse<Object>> registerUser(@RequestBody UserRequest userInfoDto) {
        return userRegisterService.registerNewUser(userInfoDto);
    }

    @GetMapping(value = "/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) throws Exception {
        // From the HttpRequest get the claims
        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

        Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
        String token = jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
        Map<String, Object> expectedMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            expectedMap.put(entry.getKey(), entry.getValue());
        }
        return expectedMap;
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
