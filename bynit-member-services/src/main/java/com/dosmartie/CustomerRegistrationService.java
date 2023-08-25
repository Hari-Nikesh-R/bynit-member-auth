package com.dosmartie;

import com.dosmartie.request.UserRequest;
import com.dosmartie.response.BaseResponse;
import org.springframework.http.ResponseEntity;

public interface CustomerRegistrationService {
    ResponseEntity<BaseResponse<Object>> registerNewUser(UserRequest userRequest);
}
