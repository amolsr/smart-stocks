package com.smartstocks.product.dto;

import com.smartstocks.product.models.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NonCreditTransactionDto {

    private LocalDateTime transactionsDate;
    private String symbol;
    private int units;
    private TransactionType type;
    private double price;
    private double profit;
}
