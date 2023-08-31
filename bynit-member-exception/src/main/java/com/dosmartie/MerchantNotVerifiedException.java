package com.dosmartie;

public class MerchantNotVerifiedException extends RuntimeException {
    public MerchantNotVerifiedException() {
        super("Merchant Not verified, Please wait for verification");
    }
}
