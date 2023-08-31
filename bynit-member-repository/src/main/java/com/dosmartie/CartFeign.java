package com.dosmartie;

import com.dosmartie.helper.feignurls.CartUrls;
import com.dosmartie.request.cart.CartOrderRequest;
import com.dosmartie.request.cart.CartRequest;
import com.dosmartie.response.BaseResponse;
import com.dosmartie.response.product.CartProductResponse;
import com.dosmartie.response.product.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dosmartie.helper.product.Constants.AUTH_ID;
import static com.dosmartie.helper.product.Constants.MERCHANT;

@FeignClient(value = "cartService", url = "http://localhost:8043/")
public interface CartFeign {

    @PostMapping(value = CartUrls.ORDER)
    public ResponseEntity<?> placeOrder(@RequestBody CartOrderRequest orderRequest, @RequestHeader(AUTH_ID) String authId);
    @PostMapping(value = CartUrls.CART + CartUrls.ADD_PRODUCTS)
    public ResponseEntity<?> addToCart(@Valid @RequestBody CartRequest cartRequest, @RequestHeader(AUTH_ID) String authId);

    @DeleteMapping(value = CartUrls.CART)
    public BaseResponse<Object> clearCart(@RequestHeader(AUTH_ID) String authId, @RequestHeader(MERCHANT) String email);

    @DeleteMapping(value = CartUrls.CART + CartUrls.DELETE_ITEM + "/{itemSku}")
    public BaseResponse<?> deleteCartItem(@RequestParam("email") String email, @PathVariable("itemSku") String itemSku, @RequestHeader(AUTH_ID) String authId);

    @DeleteMapping(value = CartUrls.CART + CartUrls.CLEAR_CART)
    public BaseResponse<List<CartProductResponse>> clearCartItem(@RequestParam("email") String email, @RequestHeader(AUTH_ID) String authId);

    @GetMapping(value = CartUrls.CART + CartUrls.VIEW)
    public BaseResponse<List<CartProductResponse>> getCartItems(@RequestParam("email") String email, @RequestHeader(AUTH_ID) String authId);
}
