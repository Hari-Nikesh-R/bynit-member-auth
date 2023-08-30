package com.dosmartie;

public class TokenExpiredException extends RuntimeException{
    public TokenExpiredException() {
        super("Token Expired");
    }
}
