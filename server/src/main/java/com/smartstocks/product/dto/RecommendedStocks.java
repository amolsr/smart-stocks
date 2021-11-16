package com.smartstocks.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecommendedStocks {

    private String symbol;
    private String longName;
    private String shortName;
    private DetailValuesDto currentPrice;
    private DetailValuesDto previousClose;
    private DetailValuesDto change;
    private DetailValuesDto changePercentage;

}
