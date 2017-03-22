package com.udacity.stockhawk.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class ListWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(getApplicationContext());
    }

}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Cursor cursor;
    private Context context;

    DecimalFormat percentageFormat;

    ListRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

        String[] projection = Contract.Quote.QUOTE_COLUMNS.toArray(new String[Contract.Quote.QUOTE_COLUMNS.size()]);

        // Get the Cursor
        cursor = context.getContentResolver().query(
                Contract.Quote.URI,
                Contract.Quote.QUOTE_COLUMNS.toArray(new String[Contract.Quote.QUOTE_COLUMNS.size()]),
                null,
                null,
                Contract.Quote.COLUMN_SYMBOL);

        percentageFormat = (DecimalFormat) NumberFormat.getPercentInstance(Locale.getDefault());
        percentageFormat.setMaximumFractionDigits(2);
        percentageFormat.setMinimumFractionDigits(2);
        percentageFormat.setPositivePrefix("+");

    }

    @Override
    public void onDataSetChanged() {

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
        remoteViews.setTextViewText(R.id.symbol, cursor.getString(Contract.Quote.POSITION_SYMBOL));

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

}
