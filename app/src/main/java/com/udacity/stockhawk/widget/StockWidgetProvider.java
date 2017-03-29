package com.udacity.stockhawk.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.udacity.stockhawk.R;
import com.udacity.stockhawk.ui.StockDetailActivity;

import timber.log.Timber;

public class StockWidgetProvider extends AppWidgetProvider {

    public static final String CLICK_ACTION = "com.udacity.stockhawk.listwidget.CLICK_ACTION";
    public static final String EXTRA_STOCK_SYMBOL = "symbol_key";

    public static void notifyWidgetDataChanged(Context context) {

        AppWidgetManager widgetManager = AppWidgetManager.getInstance(context);

        ComponentName componentName = new ComponentName(context, StockWidgetProvider.class);
        int[] appWidgetIds = widgetManager.getAppWidgetIds(componentName);

        widgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_widget);

    }

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(CLICK_ACTION)) {

            String symbol = intent.getStringExtra(EXTRA_STOCK_SYMBOL);

            Intent displayStockDetailActivity = new Intent(context, StockDetailActivity.class);
            displayStockDetailActivity.putExtra(EXTRA_STOCK_SYMBOL, symbol);
            displayStockDetailActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(displayStockDetailActivity);
            
        }

        super.onReceive(context, intent);

    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {

            Intent intent = new Intent(context, ListWidgetService.class);

            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.list_view_widget);
            remoteViews.setRemoteAdapter(R.id.lv_widget, intent);

            // Sets up the individual list item's pending intent
            Intent displayStockDetailIntent = new Intent(context, StockWidgetProvider.class);
            displayStockDetailIntent.setAction(StockWidgetProvider.CLICK_ACTION);

            PendingIntent detailPendingIntent = PendingIntent.getBroadcast(context, 0,
                    displayStockDetailIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.lv_widget, detailPendingIntent);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }
}
