package com.udacity.stockhawk.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.data.Contract;

import timber.log.Timber;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private final int LOADER_ID = 0;
    private String stockSymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
