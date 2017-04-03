package com.udacity.stockhawk.utils;

import android.content.Context;
import android.support.annotation.IntDef;

import com.udacity.stockhawk.data.PrefUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class StockUtils {

    public static final int STATUS_OK = 0;
    public static final int STATUS_DUPLICATE_EXISTS = 1;
    public static final int STATUS_STOCK_DOES_NOT_EXIST = 2;
    public static final int STATUS_NETWORK_ERROR = 3;

    /**
     * Must be called asynchronously since there is a network call with YahooUtils
     */
    public static int validStockSymbol(Context context, String symbol) {

        // Already added
        boolean duplicateExists = PrefUtils.checkStockExistsPref(context, symbol);

        if (duplicateExists) {
            return STATUS_DUPLICATE_EXISTS;
        }

        // Check if Yahoo can identify the symbol
        int yahooStatus = YahooUtils.checkStockExistsYahoo(symbol);

        if (yahooStatus == YahooUtils.STOCK_DOES_NOT_EXIST) {
            return STATUS_STOCK_DOES_NOT_EXIST;
        } else if (yahooStatus == YahooUtils.NETWORK_ERROR) {
            return STATUS_NETWORK_ERROR;
        } else if (yahooStatus == YahooUtils.STOCK_EXISTS) {
            return STATUS_OK;
        }

        throw new UnsupportedOperationException("Invalid Status");

    }

}
