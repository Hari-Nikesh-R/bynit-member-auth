package com.dosmartie.request.cart;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetailRequest {
    @NotNull(message = "Name should not be null")
    private String name;
    private String phoneNumber;
    private AddressRequest address;
}
