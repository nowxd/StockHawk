package com.udacity.stockhawk.utils;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.udacity.stockhawk.model.HistoryData;
import com.udacity.stockhawk.ui.CurrencyAxisValueFormatter;
import com.udacity.stockhawk.ui.DateAxisValueFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class ChartUtils {

    public static void setUpStockChart(LineChart stockChart, String historyDataStr) {

        List<HistoryData> dataList = parseHistoryData(historyDataStr);
        List<Entry> entries = ChartUtils.parseEntryData(dataList);

        LineDataSet dataSet = new LineDataSet(entries, "Stock Price");
        dataSet.setValueTextColor(Color.WHITE);

        LineData lineData = new LineData(dataSet);
        stockChart.setData(lineData);

        stockChart.getXAxis().setValueFormatter(new DateAxisValueFormatter(dataList));
        stockChart.getAxisLeft().setValueFormatter(new CurrencyAxisValueFormatter());
        stockChart.getAxisRight().setValueFormatter(new CurrencyAxisValueFormatter());

        editChartDesign(stockChart);

        stockChart.invalidate();

    }

    private static List<HistoryData> parseHistoryData(String historyDataStr) {

        StringTokenizer stringTokenizer = new StringTokenizer(historyDataStr, ",\n");

        List<HistoryData> dataList = new ArrayList<>();

        while (stringTokenizer.hasMoreTokens()) {

            long timeInMS = Long.parseLong(stringTokenizer.nextToken());
            double stockPrice = Double.parseDouble(stringTokenizer.nextToken());

            dataList.add(new HistoryData(timeInMS, stockPrice));

        }

        // The data that is given is in reverse chronological order
        Collections.reverse(dataList);

        return dataList;

    }


    private static List<Entry> parseEntryData(List<HistoryData> dataList) {

        List<Entry> entries = new ArrayList<>(dataList.size());

        // Using the index as the point in the X Axis
        int index = 0;

        for (HistoryData historyData : dataList) {

            entries.add(new Entry((float) (index++), (float) historyData.getStockPrice()));

        }

        return entries;

    }

    private static void editChartDesign(LineChart stockChart) {

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
