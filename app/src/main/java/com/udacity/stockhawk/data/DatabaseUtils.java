package com.udacity.stockhawk.data;

import java.io.IOException;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class DatabaseUtils {

    /**
     * @param stockSymbol symbol of the stock to be added
     * @return Boolean on whether the add was successful or not
     */
    public static boolean addSingleStock(String stockSymbol) {

        try {

            Stock stock = YahooFinance.get(stockSymbol);

            // If the stock could not be found with the symbol provided
            if (stock.getName() == null) {
                return false;
            }

        } catch(IOException e) {
            e.printStackTrace();
        }
        return true;

    }

}
