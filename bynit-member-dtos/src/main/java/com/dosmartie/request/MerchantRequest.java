package com.dosmartie.request;


import com.dosmartie.response.AddressResponse;
import lombok.Data;

@Data
public class MerchantRequest extends UserRequest {
    private AddressResponse companyAddress;
    private String companyName;
}
