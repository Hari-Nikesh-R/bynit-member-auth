package com.dosmartie.response;


import com.dosmartie.request.CompanyRequest;
import com.dosmartie.request.UserRequest;
import lombok.Data;

@Data
public class MerchantResponse extends UserResponse {
    private AddressResponse address;
    private CompanyResponse companyName;
}
