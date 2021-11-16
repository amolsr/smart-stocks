package com.smartstocks.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopStocks {
    private Double overallChangePerc;
    private Double overalLChange;
    private String symbol;
    private String companyName;
    private String HighPriceRange;

    public TopStocks(Double overallChangePerc, Double overalLChange, String symbol) {
        this.overallChangePerc = overallChangePerc;
        this.overalLChange = overalLChange;
        this.symbol = symbol;
    }
}
