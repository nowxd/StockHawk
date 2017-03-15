package com.udacity.stockhawk.utils;

import android.content.Context;
import android.support.annotation.IntDef;

import com.udacity.stockhawk.data.PrefUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class StockUtils {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_OK, STATUS_DUPLICATE_EXISTS, STATUS_STOCK_DOES_NOT_EXIST})
    public @interface StockStatus {}
    public static final int STATUS_OK = 0;
    public static final int STATUS_DUPLICATE_EXISTS = 1;
    public static final int STATUS_STOCK_DOES_NOT_EXIST = 2;

    /**
     * Must be called asynchronously since there is a network call with YahooUtils
     */
    public static int validStockSymbol(Context context, String symbol) {

        // Duplicate
        boolean duplicateExists = PrefUtils.checkStockExistsPref(context, symbol);
        if (duplicateExists) return STATUS_DUPLICATE_EXISTS;

        // Exists on their end
        boolean stockExists = YahooUtils.checkStockExistsYahoo(symbol);
        if (!stockExists) return STATUS_STOCK_DOES_NOT_EXIST;

        return STATUS_OK;

    }

}
