package com.dosmartie.response;

import com.dosmartie.request.UserRequest;
import lombok.Data;

import java.util.List;

@Data
public class CustomerResponse extends UserResponse {
    private AddressResponse address;
}
