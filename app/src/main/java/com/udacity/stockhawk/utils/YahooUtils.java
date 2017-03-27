package com.udacity.stockhawk.utils;

import java.io.IOException;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class YahooUtils {

    public static final int STOCK_EXISTS = 0;
    public static final int STOCK_DOES_NOT_EXIST = 1;
    public static final int NETWORK_ERROR = 2;

    // Check if the stock exists on Yahoo's end
    public static int checkStockExistsYahoo(String symbol) {

        try {

            Stock stock = YahooFinance.get(symbol);
            boolean validStock = stock != null && stock.getName() != null;

            if (validStock) {
                return STOCK_EXISTS;
            } else {
                return STOCK_DOES_NOT_EXIST;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return NETWORK_ERROR;
        }

    }

}
