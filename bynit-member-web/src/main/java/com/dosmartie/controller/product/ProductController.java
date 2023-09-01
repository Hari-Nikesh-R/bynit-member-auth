package com.dosmartie.controller.product;


import com.dosmartie.ProductFeign;
import com.dosmartie.authconfig.JwtTokenUtil;
import com.dosmartie.helper.PropertiesCollector;
import com.dosmartie.helper.feignurls.ProductUrls;
import com.dosmartie.request.product.CartProductRequest;
import com.dosmartie.request.product.OrderRatingRequest;
import com.dosmartie.request.product.ProductCreateRequest;
import com.dosmartie.response.BaseResponse;
import com.dosmartie.response.product.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

import static com.dosmartie.helper.product.Constants.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@RestController
@RequestMapping(value = ProductUrls.PRODUCT)
public class ProductController {
    @Autowired
    private ProductFeign productFeign;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PropertiesCollector propertiesCollector;


    @PreAuthorize("hasAuthority('MERCHANT')")
    @PostMapping
    public ResponseEntity<BaseResponse<String>> addProduct(@RequestHeader(AUTHORIZATION) String token, @RequestBody @Valid ProductCreateRequest product) throws IOException {
        product.setMerchantEmail(jwtTokenUtil.getUsernameFromToken(token.substring(7)));
        return productFeign.addProduct(product, propertiesCollector.getAuthId());
    }

    @PreAuthorize("hasAuthority('MERCHANT') or hasAuthority('CUSTOMER')")
    @GetMapping(value = "/{category}")
    public ResponseEntity<BaseResponse<List<ProductResponse>>> getProductViaCategory(@RequestHeader(AUTHORIZATION) String token, @PathVariable("category") String category, @RequestParam(PAGE) int page, @RequestParam(SIZE) int size) {
        return productFeign.getProductViaCategory(category, page, size, getMerchantEmail(token), propertiesCollector.getAuthId());
    }

    @PreAuthorize("hasAuthority('MERCHANT') or hasAuthority('CUSTOMER')")
    @GetMapping
    public ResponseEntity<BaseResponse<List<ProductResponse>>> getProduct(@RequestHeader(AUTHORIZATION) String token, @RequestParam(SEARCH_PARAM) String searchParam, @RequestParam(PAGE) int page, @RequestParam(SIZE) int size) {
        return productFeign.getProduct(searchParam, page, size, getMerchantEmail(token), propertiesCollector.getAuthId());
    }

    @PreAuthorize("hasAuthority('MERCHANT')")
    @DeleteMapping(value = "/variant")
    public ResponseEntity<BaseResponse<String>> deleteProductVariant(@RequestHeader(AUTHORIZATION) String token, @RequestParam(VARIANT_SKU) String itemSku) {
        return productFeign.deleteProductVariant(itemSku, propertiesCollector.getAuthId(), jwtTokenUtil.getUsernameFromToken(token.substring(7)));
    }

    @PreAuthorize("hasAuthority('MERCHANT')")
    @DeleteMapping
    public ResponseEntity<BaseResponse<String>> deleteProduct(@RequestHeader(AUTHORIZATION) String token, @RequestParam(DEFAULT_SKU) String itemSku) {
        return productFeign.deleteProduct(itemSku, propertiesCollector.getAuthId(), jwtTokenUtil.getUsernameFromToken(token.substring(7)));
    }

    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @DeleteMapping(value = ProductUrls.ALL_PRODUCT)
    public ResponseEntity<BaseResponse<String>> deleteAllProduct(@RequestHeader(AUTHORIZATION) String token) {
        return productFeign.deleteAllProduct(propertiesCollector.getAuthId(), jwtTokenUtil.getUsernameFromToken(token.substring(7)));
    }

    @PreAuthorize("hasAuthority('MERCHANT') or hasAuthority('CUSTOMER')")
    @GetMapping(value = ProductUrls.ALL_PRODUCT)
    public BaseResponse<List<ProductResponse>> getAllProducts(@RequestHeader(AUTHORIZATION) String token, @RequestParam(PAGE) int page, @RequestParam(SIZE) int size) {
        return productFeign.getAllProducts(page, size, getMerchantEmail(token), propertiesCollector.getAuthId());
    }

    @PreAuthorize("hasAuthority('MERCHANT')")
    @PostMapping(value = ProductUrls.STOCK)
    public ResponseEntity<BaseResponse<String>> updateStock(@RequestBody CartProductRequest cartProductRequest) {
        return productFeign.updateStock(cartProductRequest, propertiesCollector.getAuthId());
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PutMapping(value = ProductUrls.RATE)
    public ResponseEntity<BaseResponse<?>> rateProduct(@RequestHeader(AUTHORIZATION) String token, @RequestBody @Valid OrderRatingRequest orderRatingRequest) {
        return productFeign.rateProduct(orderRatingRequest, propertiesCollector.getAuthId(), jwtTokenUtil.getUsernameFromToken(token));
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping(value = ProductUrls.RATE)
    public ResponseEntity<BaseResponse<?>> getUnratedProducts(@RequestHeader(AUTHORIZATION) String token, @RequestParam("orderId") String orderId) {
        return productFeign.fetchUnratedProductsFromAnOrder(propertiesCollector.getAuthId(), orderId, jwtTokenUtil.getUsernameFromToken(token));
    }

    private String getMerchantEmail(String token) {
        if (jwtTokenUtil.getRoleFromToken(token.substring(7)).equalsIgnoreCase("MERCHANT")) {
            return jwtTokenUtil.getUsernameFromToken(token.substring(7));
        }
        return null;
    }

}
