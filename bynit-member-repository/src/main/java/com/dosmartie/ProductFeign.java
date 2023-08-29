package com.dosmartie;

import com.dosmartie.helper.feignurls.ProductUrls;
import com.dosmartie.request.product.CartProductRequest;
import com.dosmartie.request.product.ProductCreateRequest;
import com.dosmartie.response.BaseResponse;
import com.dosmartie.response.product.ProductQuantityCheckResponse;
import com.dosmartie.response.product.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.dosmartie.helper.product.Constants.*;

@FeignClient(value = "productService", url = "http://localhost:8042/product/")
public interface ProductFeign {
    @PostMapping
    public ResponseEntity<BaseResponse<String>> addProduct(@RequestBody @Valid ProductCreateRequest product, @RequestHeader(AUTH_ID) String authId);

    @GetMapping(value = "/{category}")
    public ResponseEntity<BaseResponse<List<ProductResponse>>> getProductViaCategory(@PathVariable("category") String category, @RequestParam(PAGE) int page, @RequestParam(SIZE) int size, @RequestParam(value = MERCHANT, required = false) String merchant);

    @GetMapping
    public ResponseEntity<BaseResponse<List<ProductResponse>>> getProduct(@RequestParam(SEARCH_PARAM) String searchParam, @RequestParam(PAGE) int page, @RequestParam(SIZE) int size, @RequestParam(value = MERCHANT, required = false) String merchant, @RequestHeader(AUTH_ID) String authId);

    @DeleteMapping(value = "/variant")
    public ResponseEntity<BaseResponse<String>> deleteProductVariant(@RequestParam(VARIANT_SKU) String itemSku, @RequestHeader(AUTH_ID) String authId, @RequestHeader(MERCHANT) String email);

    @DeleteMapping
    public ResponseEntity<BaseResponse<String>> deleteProduct(@RequestParam(DEFAULT_SKU) String itemSku, @RequestHeader(AUTH_ID) String authId, @RequestHeader(MERCHANT) String email);

    @DeleteMapping(value = ProductUrls.ALL_PRODUCT)
    public ResponseEntity<BaseResponse<String>> deleteAllProduct(@RequestHeader(AUTH_ID) String authId, @RequestHeader(MERCHANT) String email);

    @GetMapping(value = ProductUrls.ALL_PRODUCT)
    public BaseResponse<List<ProductResponse>> getAllProducts(@RequestParam(PAGE) int page, @RequestParam(SIZE) int size, @RequestParam(value = MERCHANT, required = false) String merchant, @RequestHeader(AUTH_ID) String authId);

    @PostMapping(value = ProductUrls.QUANTITY)
    public ProductQuantityCheckResponse[] quantityCheck(@RequestBody List<CartProductRequest> productRequest);
}
