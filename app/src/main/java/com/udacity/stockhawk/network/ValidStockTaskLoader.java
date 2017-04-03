package com.udacity.stockhawk.network;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.udacity.stockhawk.utils.StockUtils;

/**
 * Check if the stock exists on Yahoo's end
 */
public class ValidStockTaskLoader extends AsyncTaskLoader<Integer> {

    private String symbol;

    public ValidStockTaskLoader(String symbol, Context context) {
        super(context);
        this.symbol = symbol;
    }

    @Override
    public Integer loadInBackground() {

        if (symbol == null || symbol.isEmpty()) return null;

        return StockUtils.validStockSymbol(getContext(), symbol);

    }
}
