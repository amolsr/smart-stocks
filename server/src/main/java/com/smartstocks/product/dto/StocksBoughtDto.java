package com.smartstocks.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StocksBoughtDto {

    private String symbol;
    private int units;
    private double price;
    private double high;
    private double low;
    private double previousClose;
}
