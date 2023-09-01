//package com.dosmartie.controller.order;
//
//import com.dosmartie.OrderFeign;
//import com.dosmartie.authconfig.JwtTokenUtil;
//import com.dosmartie.helper.PropertiesCollector;
//import com.dosmartie.response.BaseResponse;
//import com.dosmartie.response.order.OrderResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Objects;
//
//import static com.dosmartie.helper.feignurls.CartUrls.ORDER;
//import static com.dosmartie.helper.feignurls.OrderUrls.ADMIN_ALL;
//import static com.dosmartie.helper.feignurls.OrderUrls.ALL;
//import static org.springframework.http.HttpHeaders.AUTHORIZATION;
//
//@RestController
//@RequestMapping(value = ORDER)
//public class OrderController {
//
//    @Autowired
//    private OrderFeign orderFeign;
//    @Autowired
//    private PropertiesCollector propertiesCollector;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//
//    @PreAuthorize("hasAuthority('CUSTOMER')")
//    @GetMapping(value = ALL)
//    public ResponseEntity<BaseResponse<List<OrderResponse>>> getProductByEmail(@RequestHeader(AUTHORIZATION) String token) {
//        return orderFeign.getProductByEmail(jwtTokenUtil.getUsernameFromToken(token.substring(7)));
//    }
//
//    @PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('ADMIN')")
//    @GetMapping(value = ADMIN_ALL)
//    public ResponseEntity<BaseResponse<List<OrderResponse>>> getAllOrder() {
//        return orderFeign.getAllOrder(propertiesCollector.getAuthId());
//    }
//    @PreAuthorize("hasAuthority('CUSTOMER')")
//    @GetMapping
//    public ResponseEntity<BaseResponse<OrderResponse>> getOrder(@RequestHeader(AUTHORIZATION) String token, @RequestParam("orderId") String orderId) {
//        return orderFeign.getOrder(orderId, jwtTokenUtil.getUsernameFromToken(token));
//    }
//}
