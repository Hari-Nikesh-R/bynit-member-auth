package com.dosmartie.response;


import com.dosmartie.request.UserRequest;
import lombok.Data;

@Data
public class MerchantResponse extends UserResponse {
    private AddressResponse companyAddress;
    private String companyName;

}
