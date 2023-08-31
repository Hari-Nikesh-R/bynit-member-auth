package com.dosmartie.request.cart;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartOrderRequest {
    @Valid
    private CustomerDetailRequest orderedCustomerDetail;
    private String email;
}
