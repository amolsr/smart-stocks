package com.smartstocks.product.repository;

import com.smartstocks.product.models.StockTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockTransactionsRepository extends JpaRepository<StockTransactions, Long> {
}
