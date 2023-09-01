package com.dosmartie;

import com.dosmartie.helper.feignurls.ProductUrls;
import com.dosmartie.request.product.CartProductRequest;
import com.dosmartie.request.product.OrderRatingRequest;
import com.dosmartie.request.product.ProductCreateRequest;
import com.dosmartie.response.BaseResponse;
import com.dosmartie.response.product.ProductQuantityCheckResponse;
import com.dosmartie.response.product.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.dosmartie.helper.product.Constants.*;

@FeignClient(value = "productService", url = "http://localhost:8042/")
public interface ProductFeign {
    @PostMapping(value = ProductUrls.PRODUCT)
    ResponseEntity<BaseResponse<String>> addProduct(@RequestBody @Valid ProductCreateRequest product, @RequestHeader(AUTH_ID) String authId);

    @GetMapping(value = ProductUrls.PRODUCT + "/{category}")
    ResponseEntity<BaseResponse<List<ProductResponse>>> getProductViaCategory(@PathVariable("category") String category, @RequestParam(PAGE) int page, @RequestParam(SIZE) int size, @RequestParam(value = MERCHANT, required = false) String merchant,  @RequestHeader(AUTH_ID) String authId);

    @GetMapping(value = ProductUrls.PRODUCT)
    ResponseEntity<BaseResponse<List<ProductResponse>>> getProduct(@RequestParam(SEARCH_PARAM) String searchParam, @RequestParam(PAGE) int page, @RequestParam(SIZE) int size, @RequestParam(value = MERCHANT, required = false) String merchant, @RequestHeader(AUTH_ID) String authId);

    @DeleteMapping(value = ProductUrls.PRODUCT + "/variant")
    ResponseEntity<BaseResponse<String>> deleteProductVariant(@RequestParam(VARIANT_SKU) String itemSku, @RequestHeader(AUTH_ID) String authId, @RequestHeader(MERCHANT) String email);

    @DeleteMapping(value = ProductUrls.PRODUCT)
    ResponseEntity<BaseResponse<String>> deleteProduct(@RequestParam(DEFAULT_SKU) String itemSku, @RequestHeader(AUTH_ID) String authId, @RequestHeader(MERCHANT) String email);

    @DeleteMapping(value = ProductUrls.PRODUCT + ProductUrls.ALL_PRODUCT)
    ResponseEntity<BaseResponse<String>> deleteAllProduct(@RequestHeader(AUTH_ID) String authId, @RequestHeader(MERCHANT) String email);

    @GetMapping(value = ProductUrls.PRODUCT + ProductUrls.ALL_PRODUCT)
    BaseResponse<List<ProductResponse>> getAllProducts(@RequestParam(PAGE) int page, @RequestParam(SIZE) int size, @RequestParam(value = MERCHANT, required = false) String merchant, @RequestHeader(AUTH_ID) String authId);


    @PostMapping(value = ProductUrls.PRODUCT + ProductUrls.STOCK)
    ResponseEntity<BaseResponse<String>> updateStock(@RequestBody CartProductRequest cartProductRequest, @RequestHeader(AUTH_ID) String authId);

    @PutMapping(value = ProductUrls.PURCHASE + ProductUrls.RATE)
    ResponseEntity<BaseResponse<?>> rateProduct(@RequestBody @Valid OrderRatingRequest orderRatingRequest, @RequestHeader(AUTH_ID) String authId, @RequestHeader("email") String email);
    @GetMapping(value = ProductUrls.PURCHASE + ProductUrls.RATE)
    public ResponseEntity<BaseResponse<?>> fetchUnratedProductsFromAnOrder(@RequestHeader(AUTH_ID) String authId, @RequestParam("orderId") String orderId, @RequestHeader("email") String email);
}
