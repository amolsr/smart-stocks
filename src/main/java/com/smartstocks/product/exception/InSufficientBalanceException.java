package com.smartstocks.product.exception;

public class InSufficientBalanceException extends RuntimeException{
    public InSufficientBalanceException(String message) {
        super(message);
    }
}
