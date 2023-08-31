package com.dosmartie.response.order;

import com.dosmartie.request.cart.CustomerDetailRequest;
import com.dosmartie.response.product.ProductResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    private double totalOrder;
    private CustomerDetailRequest orderedCustomerDetail;
    private OrderStatus orderStatus;
    private String orderId;
    private List<ProductResponse> availableProduct;
    private String errorDesc;

    public OrderResponse(String errorDesc) {
        this.errorDesc = errorDesc;
    }
}
