package com.smartstocks.product.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class MockStocks {
    public static Map<String, String> mockStocksMap = new LinkedHashMap<>();
    static {
        mockStocksMap.put("MRF.NS", "Stock A");
        mockStocksMap.put("RELIANCE.NS", "Stock B");
        mockStocksMap.put("TATASTEEL.NS", "Stock C");
        mockStocksMap.put("ZOMATO.NS", "Stock D");
        mockStocksMap.put("ONGC.NS", "Stock E");
    }
}
