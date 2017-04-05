package com.udacity.stockhawk.utils;

import java.io.IOException;

import timber.log.Timber;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

class YahooUtils {

    static final int STOCK_EXISTS = 0;
    static final int STOCK_DOES_NOT_EXIST = 1;
    static final int NETWORK_ERROR = 2;

    // Check if the stock exists on Yahoo's end
    static int checkStockExistsYahoo(String symbol) {

        try {

            // First check if we received a valid input
            if (!checkSymbolIsValid(symbol)) {
                return STOCK_DOES_NOT_EXIST;
            }

            // Then check if the stock exists
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

    private static boolean checkSymbolIsValid(String symbol) {

        // The input string must only contain letters from the roman alphabet
        final String regex = "[a-zA-Z]+";

        return symbol.matches(regex);

    }

}
