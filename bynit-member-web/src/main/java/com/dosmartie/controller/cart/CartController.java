//package com.dosmartie.controller.cart;
//
//import com.dosmartie.CartFeign;
//import com.dosmartie.authconfig.JwtTokenUtil;
//import com.dosmartie.helper.feignurls.CartUrls;
//import com.dosmartie.request.cart.CartOrderRequest;
//import com.dosmartie.request.cart.CartRequest;
//import com.dosmartie.response.BaseResponse;
//import com.dosmartie.response.product.CartProductResponse;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//import static org.springframework.http.HttpHeaders.AUTHORIZATION;
//
//@RestController
//@RequestMapping(value = CartUrls.CART)
//public class CartController {
//    @Autowired
//    private CartFeign cartFeign;
//
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @PreAuthorize("hasAuthority('CUSTOMER')")
//    @PostMapping(value = CartUrls.ADD_PRODUCTS)
//    public ResponseEntity<?> addToCart(@RequestHeader(AUTHORIZATION) String token, @Valid @RequestBody CartRequest cartRequest) {
//        cartRequest.setUserEmail(jwtTokenUtil.getUsernameFromToken(token.substring(7)));
//        return cartFeign.addToCart(cartRequest);
//    }
//
//    @PreAuthorize("hasAuthority('CUSTOMER')")
//    @PostMapping(value = CartUrls.ORDER)
//    public ResponseEntity<?> placeOrder(@RequestHeader(AUTHORIZATION) String token, @Valid @RequestBody CartOrderRequest cartOrderRequest) {
//        cartOrderRequest.setEmail(jwtTokenUtil.getUsernameFromToken(token.substring(7)));
//        return cartFeign.placeOrder(cartOrderRequest);
//    }
//
//    @PreAuthorize("hasAuthority('CUSTOMER')")
//    @DeleteMapping
//    public BaseResponse<Object> clearCart(@RequestHeader(AUTHORIZATION) String token) {
//        return cartFeign.clearCart( jwtTokenUtil.getUsernameFromToken(token.substring(7)));
//    }
//
//    @PreAuthorize("hasAuthority('CUSTOMER')")
//    @DeleteMapping(value = CartUrls.DELETE_ITEM + "/{itemSku}")
//    public BaseResponse<?> deleteCartItem(@RequestHeader(AUTHORIZATION) String token, @PathVariable("itemSku") String itemSku) {
//        return cartFeign.deleteCartItem(jwtTokenUtil.getUsernameFromToken(token.substring(7)), itemSku);
//    }
//
//    @PreAuthorize("hasAuthority('CUSTOMER')")
//    @DeleteMapping(value = CartUrls.CLEAR_CART)
//    public BaseResponse<List<CartProductResponse>> clearCartItem(@RequestHeader(AUTHORIZATION) String token) {
//        return cartFeign.clearCartItem(jwtTokenUtil.getUsernameFromToken(token.substring(7)));
//    }
//
//    @PreAuthorize("hasAuthority('CUSTOMER')")
//    @GetMapping(value = CartUrls.VIEW)
//    public BaseResponse<List<CartProductResponse>> getCartItems(@RequestHeader(AUTHORIZATION) String token) {
//        return cartFeign.getCartItems(jwtTokenUtil.getUsernameFromToken(token.substring(7)));
//    }
//}
