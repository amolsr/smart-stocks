package com.smartstocks.product.service.impl;

import com.smartstocks.product.models.SearchTable;
import com.smartstocks.product.repository.SearchTableRepository;
import com.smartstocks.product.service.ISerachTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchTableServiceImpl implements ISerachTableService {

    @Autowired
    private SearchTableRepository searchTableRepository;

    @Override
    public List<SearchTable> searchStock(String query, int limit) {
        return searchTableRepository.search(query, limit);
    }

    @Override
    public SearchTable getStockBySymbol(String symbol) {
        return searchTableRepository.getBySymbol(symbol);
    }

    @Override
    public Page<SearchTable> allStocks(int pageNo, int size) {
        return this.searchTableRepository.getAll(PageRequest.of(pageNo, size));
    }
}
