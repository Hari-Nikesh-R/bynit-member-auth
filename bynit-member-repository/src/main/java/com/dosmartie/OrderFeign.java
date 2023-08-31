package com.dosmartie;

import com.dosmartie.response.BaseResponse;
import com.dosmartie.response.order.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.dosmartie.helper.feignconstants.OrderConstants.PARAM;
import static com.dosmartie.helper.feignurls.OrderUrls.ALL;
import static com.dosmartie.helper.product.Constants.AUTH_ID;

@FeignClient(url = "http://localhost:8085/order", value = "orderFeign")
public interface OrderFeign {
    @GetMapping
    public ResponseEntity<BaseResponse<OrderResponse>> getProductByEmail(@RequestParam(value = PARAM) String param, @RequestHeader(AUTH_ID) String authId);
    @GetMapping(value = ALL)
    public ResponseEntity<BaseResponse<List<OrderResponse>>> getAllOrder(@RequestHeader(AUTH_ID) String authId);
}
