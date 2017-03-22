package com.udacity.stockhawk.utils;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.udacity.stockhawk.model.HistoryData;
import com.udacity.stockhawk.ui.DateAxisValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class ChartUtils {

    public static void setUpStockChart(LineChart stockChart, String historyDataStr) {

        List<HistoryData> dataList = parseHistoryData(historyDataStr);
        List<Entry> entries = ChartUtils.parseEntryData(dataList);

        LineDataSet dataSet = new LineDataSet(entries, "Stock Price");
        dataSet.setValueTextColor(Color.WHITE);

        LineData lineData = new LineData(dataSet);
        stockChart.setData(lineData);

        String[] dates = parseDates(dataList);
        stockChart.getXAxis().setValueFormatter(new DateAxisValueFormatter(dates));

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

    /**
     * Converts the timeInMS to a date string that will be displayed in the chart
     */
    private static String[] parseDates(List<HistoryData> dataList) {

        int len = dataList.size();
        String[] dates = new String[len];

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-yyyy", Locale.US);

        for (int index = 0; index < len; index++) {

            calendar.setTimeInMillis(dataList.get(index).getTimeInMS());
            dates[index] = dateFormat.format(calendar.getTime());

        }

        return dates;

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
