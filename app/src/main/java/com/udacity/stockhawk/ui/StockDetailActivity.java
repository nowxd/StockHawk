package com.udacity.stockhawk.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.utils.ChartUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StockDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.tv_stock_symbol) TextView stockNameTextView;
    @BindView(R.id.lc_stock_chart) LineChart stockChart;
    @BindView(R.id.tv_no_chart_data_message) TextView noDataTextView;

    private final int LOADER_ID = 0;
    private String stockSymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();

        if (intent.hasExtra(getString(R.string.intent_extra_symbol_key))) {
            this.stockSymbol = intent.getStringExtra(getString(R.string.intent_extra_symbol_key));
            getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        // Append the symbol
        Uri uri = Contract.Quote.URI.buildUpon().appendPath(this.stockSymbol).build();
        String[] projection = Contract.Quote.QUOTE_COLUMNS.toArray(new String[Contract.Quote.QUOTE_COLUMNS.size()]);

        return new CursorLoader(this, uri, projection, null, null, null);

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data == null || data.getCount() == 0) return;

        data.moveToFirst();

        String historyString = data.getString(Contract.Quote.POSITION_HISTORY);

        stockNameTextView.setText(data.getString(Contract.Quote.POSITION_SYMBOL));

        if (!historyString.isEmpty()) {
            noDataTextView.setVisibility(View.INVISIBLE);
            stockChart.setVisibility(View.VISIBLE);
            setupChart(historyString);
        } else {
            stockChart.setVisibility(View.INVISIBLE);
            noDataTextView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void setupChart(String historyString) {
        // Check if the current configuration is RTL for the chart
        boolean RTL = (getResources().getConfiguration().getLayoutDirection() == View.LAYOUT_DIRECTION_RTL);
        ChartUtils.setUpStockChart(stockChart, historyString, RTL);
    }

}
