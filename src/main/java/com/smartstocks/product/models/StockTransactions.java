package com.smartstocks.product.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockTransactions {

    @Id
    @GeneratedValue
    private long transactionId;
    @CreationTimestamp
    private LocalDateTime transactionsDate;
    private String symbol;
    private int units;
    @Enumerated(value = EnumType.STRING)
    private TransactionType type;
    private double price;
    private double profit;
}
