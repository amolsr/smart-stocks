package com.smartstocks.product.repository;

import com.smartstocks.product.models.StocksBought;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StocksBoughtRepository extends JpaRepository<StocksBought, Long> {
    Optional<StocksBought> findBySymbol(String symbol);
}
