package com.smartstocks.product.exception;

public class InvalidStockSelection extends RuntimeException {
    public InvalidStockSelection(String message) {
        super(message);
    }
}
