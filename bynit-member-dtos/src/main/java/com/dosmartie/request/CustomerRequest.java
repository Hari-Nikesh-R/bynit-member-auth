package com.dosmartie.request;

import com.dosmartie.response.AddressResponse;
import lombok.Data;

import java.util.List;

@Data
public class CustomerRequest extends UserRequest {
    private AddressResponse addressResponse;
}
