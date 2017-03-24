package com.udacity.stockhawk.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import timber.log.Timber;

public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(getApplicationContext());
    }

}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Cursor cursor;
    private Context context;

    private DecimalFormat percentageFormat;

    ListRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

        this.cursor = queryCursor();

        percentageFormat = (DecimalFormat) NumberFormat.getPercentInstance(Locale.getDefault());
        percentageFormat.setMaximumFractionDigits(2);
        percentageFormat.setMinimumFractionDigits(2);
        percentageFormat.setPositivePrefix("+");

    }

    @Override
    public void onDataSetChanged() {
        this.cursor = queryCursor();
    }

    @Override
    public void onDestroy() {
        if (cursor != null) cursor.close();
    }

    @Override
    public int getCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {

        cursor.moveToPosition(i);

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.list_item_quote);

        // Symbol
        String symbol = cursor.getString(Contract.Quote.POSITION_SYMBOL);
        remoteViews.setTextViewText(R.id.symbol, symbol);

        // Change Color
        float rawAbsoluteChange = cursor.getFloat(Contract.Quote.POSITION_ABSOLUTE_CHANGE);

        if (rawAbsoluteChange > 0) {
            remoteViews.setInt(R.id.change, "setBackgroundResource" ,R.drawable.percent_change_pill_green);
        } else {
            remoteViews.setInt(R.id.change, "setBackgroundResource" ,R.drawable.percent_change_pill_red);
        }

        // Percent
        float percentageChange = cursor.getFloat(Contract.Quote.POSITION_PERCENTAGE_CHANGE);
        remoteViews.setTextViewText(R.id.change, percentageFormat.format(percentageChange / 100));

        // On Click
        Bundle bundle = new Bundle();
        bundle.putString(StockWidgetProvider.EXTRA_STOCK_SYMBOL, symbol);

        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(bundle);

        remoteViews.setOnClickFillInIntent(R.id.symbol, fillInIntent);

        return remoteViews;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        if (cursor == null) return 0;
        return cursor.getCount();
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private Cursor queryCursor() {

        String[] projection = Contract.Quote.QUOTE_COLUMNS.toArray(new String[Contract.Quote.QUOTE_COLUMNS.size()]);

        return context.getContentResolver().query(
                Contract.Quote.URI,
                projection,
                null,
                null,
                Contract.Quote.COLUMN_SYMBOL);

    }

}
