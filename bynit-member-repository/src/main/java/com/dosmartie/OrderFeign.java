package com.dosmartie;

import com.dosmartie.response.BaseResponse;
import com.dosmartie.response.order.OrderResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.dosmartie.helper.feignconstants.OrderConstants.EMAIL;
import static com.dosmartie.helper.feignurls.OrderUrls.ADMIN_ALL;
import static com.dosmartie.helper.feignurls.OrderUrls.ALL;
import static com.dosmartie.helper.product.Constants.AUTH_ID;

@FeignClient(url = "http://localhost:8045/order", value = "orderFeign")
public interface OrderFeign {
    @GetMapping(value = ALL)
    public ResponseEntity<BaseResponse<List<OrderResponse>>> getProductByEmail(@RequestParam(value = EMAIL) String param, @RequestHeader(AUTH_ID) String authId);
    @GetMapping
    public ResponseEntity<BaseResponse<OrderResponse>> getOrder(@RequestHeader("orderId") String orderId,  @RequestHeader("email") String email, @RequestHeader(AUTH_ID) String authId);
    @GetMapping(value = ADMIN_ALL)
    public ResponseEntity<BaseResponse<List<OrderResponse>>> getAllOrder(@RequestHeader(AUTH_ID) String authId);
}
