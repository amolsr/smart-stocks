package com.smartstocks.product.util;

import com.smartstocks.product.dto.DetailValuesDto;
import com.smartstocks.product.dto.StocksBoughtDto;
import com.smartstocks.product.dto.NonCreditTransactionDto;
import com.smartstocks.product.dto.StocksToBuy;
import com.smartstocks.product.exception.InvalidStockSelection;
import com.smartstocks.product.models.MockStocks;

import java.util.List;
import java.util.Map;

public class UtilityMethods {

    public static DetailValuesDto[] calculateChange(double currentPrice, double previousClose ) {
        DetailValuesDto change = new DetailValuesDto();
        change.setFmt(String.format("%1.2f", currentPrice-previousClose));
        DetailValuesDto changePercentage = new DetailValuesDto();
        double changePercentageValue = (currentPrice-previousClose)/previousClose*100;
        changePercentage.setFmt(String.format("%1.2f", changePercentageValue) + "%");
        return new DetailValuesDto[]{change, changePercentage};
    }

    public static List<StocksToBuy> convertIntoRealSymbol(List<StocksToBuy> stocks) {
        for(StocksToBuy ele: stocks) {
            boolean flag = false;
            for(Map.Entry entry : MockStocks.mockStocksMap.entrySet()) {
                if(entry.getValue().toString().compareTo(ele.getSymbol()) == 0) {
                    flag = true;
                    ele.setSymbol((String) entry.getKey());
                    break;
                }
            }
            if(!flag)  {
                throw new InvalidStockSelection(ele.getSymbol() + " is not the part of Mock Stocks");
            }
        }
        return stocks;
    }

    public static String convertIntoMockSymbolsForStocksBought(List<StocksBoughtDto> stocksBought) {
        StringBuffer symbols = new StringBuffer();
        for(StocksBoughtDto ele: stocksBought) {
            symbols.append(ele.getSymbol() + ",");
            ele.setSymbol(MockStocks.mockStocksMap.get(ele.getSymbol()));
        }
        return (symbols.length() != 0) ? symbols.substring(0, symbols.length()-1) : symbols.toString();
    }

    public static void convertIntoMockSymbolsForNonCreditTransactionDto(List<NonCreditTransactionDto> transaction) {
        for(NonCreditTransactionDto ele: transaction) {
            ele.setSymbol(MockStocks.mockStocksMap.get(ele.getSymbol()));
        }
    }

}
