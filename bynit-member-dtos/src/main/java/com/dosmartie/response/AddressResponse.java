package com.dosmartie.response;

import lombok.Data;

@Data
public class AddressResponse {
    private Integer pinCode;
    private String landmark;
    private String streetName;
}
