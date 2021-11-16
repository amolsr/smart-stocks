package com.smartstocks.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyProfileDto {

    private String shortName;
    private String longName;
    private String exchange;
    private String symbol;
    private String sector;
    private String longBusinessSummary;
    private String industry;
    private DetailValuesDto enterpriseValue;
    private DetailValuesDto currentPrice;
    private DetailValuesDto change;
    private DetailValuesDto changePercentage;
    private DetailValuesDto previousClose;
    private DetailValuesDto open;
    private DetailValuesDto dayLow;
    private DetailValuesDto dayHigh;
    private DetailValuesDto volume;
    private DetailValuesDto fiftyTwoWeekHigh;
    private DetailValuesDto fiftyTwoWeekLow;
    private DetailValuesDto marketCap;
    private DetailValuesDto forwardPE;
    private DetailValuesDto priceToBook;
    private DetailValuesDto trailingAnnualDividendYield;
}
