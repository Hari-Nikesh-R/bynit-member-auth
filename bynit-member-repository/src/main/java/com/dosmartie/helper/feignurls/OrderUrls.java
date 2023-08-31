package com.dosmartie.helper.feignurls;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderUrls {
    public static final String ORDER_ID_PARAM = "/{orderId}";
    public static final String BILL = "/bill";
    public static final String ORDER = "/order";
    public static final String ALL = "/all";
}
