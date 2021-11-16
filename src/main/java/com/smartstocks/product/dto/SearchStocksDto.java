package com.smartstocks.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchStocksDto {

    private String symbol;
    private String exchange;
    private String shortName;
    private String longName;
    private String prevName;
    private String nameChangeDate;

}
