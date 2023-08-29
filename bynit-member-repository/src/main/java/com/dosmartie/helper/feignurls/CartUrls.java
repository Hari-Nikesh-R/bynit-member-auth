package com.dosmartie.helper.feignurls;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CartUrls {
    public static final String CART = "/cart";
    public static final String ADD_PRODUCTS = "/add";
    public static final String DELETE_ITEM = "/delete-items";
    public static final String CLEAR_CART = "/clear-cart";
    public static final String ORDER = "/order";
    public static final String VIEW = "/view";
}
