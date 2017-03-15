package com.udacity.stockhawk.utils;

import java.io.IOException;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

class YahooUtils {

    // Check if the stock exists on Yahoo's end
    static boolean checkStockExistsYahoo(String symbol) {

        try {

            Stock stock = YahooFinance.get(symbol);
            return stock != null && stock.getName() != null;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;

    }

}
