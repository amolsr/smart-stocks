package com.smartstocks.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MockStockDto {
    private String companyName;
    private double price;
    private double high;
    private double low;
    private double previousClose;
}
