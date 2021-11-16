package com.smartstocks.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrendDto {
    private String period;
    private int strongBuy;
    private int buy;
    private int hold;
    private int sell;
    private int strongSell;
}
