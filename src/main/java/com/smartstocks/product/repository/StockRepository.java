package com.smartstocks.product.repository;

import com.smartstocks.product.dto.TopStocks;
import com.smartstocks.product.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

    @Query(nativeQuery = true)
    List<TopStocks> topGainers(int days);

    @Query(nativeQuery = true)
    List<TopStocks> topLosers(int days);
}
