package com.dosmartie.helper.feignurls;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductUrls {
    public static final String PRODUCT = "/product";
    public static final String GENERATE_REPORT = "/generate-report";
    public static final String ADD_PRODUCT = "/add-product";
    public static final String MAKE_ORDER = "/make-purchase";
    public static final String ALL_PRODUCT = "/all";
    public static final String PURCHASE = "/purchase";
    public static final String STOCK = "/stock";
    public static final String QUANTITY = "/quantity";
    public static final String RATE = "/rate";
    public static final String DEDUCT_STOCK = "/deductStock";
}
