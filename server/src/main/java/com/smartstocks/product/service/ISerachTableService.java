package com.smartstocks.product.service;

import com.smartstocks.product.models.SearchTable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ISerachTableService {
    List<SearchTable> searchStock(String query, int limit);
    SearchTable getStockBySymbol(String symbol);
    Page<SearchTable> allStocks(int pageNo, int size);
}
