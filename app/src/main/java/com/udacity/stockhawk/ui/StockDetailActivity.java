package com.udacity.stockhawk.ui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;
import com.udacity.stockhawk.utils.DataUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class StockDetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    @BindView(R.id.tv_stock_name) TextView stockNameTextView;
    @BindView(R.id.lc_stock_chart) LineChart stockChart;

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

        int resultCount = data.getCount();
        Timber.d("Number of results: " + resultCount);

        data.moveToFirst();

        Timber.d("STOCK SYMBOL: " + data.getString(Contract.Quote.POSITION_SYMBOL));
        Timber.d("STOCK PRICE: " + data.getDouble(Contract.Quote.POSITION_PRICE));

        String historyString = data.getString(Contract.Quote.POSITION_HISTORY);

        stockNameTextView.setText(data.getString(Contract.Quote.POSITION_SYMBOL));
        setupChart(historyString);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void setupChart(String historyString) {

        List<Entry> entries = DataUtils.parseHistoryData(historyString);

        LineDataSet dataSet = new LineDataSet(entries, "Stock Price");
        dataSet.setValueTextColor(Color.WHITE);

        LineData lineData = new LineData(dataSet);
        stockChart.setData(lineData);

        // Stock Chart styling
        stockChart.setBorderColor(Color.WHITE);

        // Axis
        stockChart.getXAxis().setTextColor(Color.WHITE);
        stockChart.getAxisLeft().setTextColor(Color.WHITE);
        stockChart.getAxisRight().setTextColor(Color.WHITE);

        // Legend
        stockChart.getLegend().setTextColor(Color.WHITE);

        // Description
        stockChart.getDescription().setText("Stock History");
        stockChart.getDescription().setTextColor(Color.WHITE);

    }

}
