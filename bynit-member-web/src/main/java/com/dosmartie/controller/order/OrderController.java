package com.dosmartie.controller.order;

import com.dosmartie.OrderFeign;
import com.dosmartie.authconfig.JwtTokenUtil;
import com.dosmartie.helper.PropertiesCollector;
import com.dosmartie.response.BaseResponse;
import com.dosmartie.response.order.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.dosmartie.helper.feignconstants.OrderConstants.PARAM;
import static com.dosmartie.helper.feignurls.CartUrls.ORDER;
import static com.dosmartie.helper.feignurls.OrderUrls.ALL;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping(value = ORDER)
public class OrderController {

    @Autowired
    private OrderFeign orderFeign;
    @Autowired
    private PropertiesCollector propertiesCollector;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @GetMapping
    public ResponseEntity<BaseResponse<OrderResponse>> getProductByEmail(@RequestHeader(AUTHORIZATION) String token, @RequestParam(value = PARAM, required = false) String param) {
        return orderFeign.getProductByEmail(Objects.isNull(param) ? jwtTokenUtil.getUsernameFromToken(token.substring(7)) : param, propertiesCollector.getAuthId());
    }

    @GetMapping(value = ALL)
    public ResponseEntity<BaseResponse<List<OrderResponse>>> getAllOrder() {
        return orderFeign.getAllOrder(propertiesCollector.getAuthId());
    }
}
