package com.smartstocks.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MoneyDto {

    @NotNull(message = "value cannot be null")
    @Min(value = 1, message = "value cannot be negative or zero")
    private double value;

}
