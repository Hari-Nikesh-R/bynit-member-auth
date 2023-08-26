package com.dosmartie;

import com.dosmartie.request.AuthRequest;
import com.dosmartie.response.AuthResponse;
import com.dosmartie.response.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<BaseResponse<AuthResponse>> signInUser(AuthRequest authRequest);
    ResponseEntity<BaseResponse<AuthResponse>> getRefreshToken(HttpServletRequest request);
}
