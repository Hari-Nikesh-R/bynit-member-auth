package com.dosmartie.request.cart;

import com.dosmartie.response.product.CartProductResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CartRequest {
    private String userEmail;
    @NotNull(message = "cartProduct is mandatory")
    @Valid
    private ProductRequest cartProduct;
}
