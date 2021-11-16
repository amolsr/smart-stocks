package com.smartstocks.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StocksToBuy {

    @NotNull(message = "symbol cannot be null")
    @NotEmpty(message = "symbol cannot be empty")
    private String symbol;
    @NotNull(message = "units cannot be empty")
    @Min(value = 1, message = "units cannot be negative or zero")
    private int units;

}
