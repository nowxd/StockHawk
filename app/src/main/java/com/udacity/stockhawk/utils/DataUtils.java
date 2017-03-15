package com.udacity.stockhawk.utils;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;


public class DataUtils {

    public static List<Entry> parseHistoryData(String historyDataStr) {

        StringTokenizer stringTokenizer = new StringTokenizer(historyDataStr, ",\n");

        List<Entry> entries = new ArrayList<>();

        // Mock points
        int cnt = 0;

        // TODO Figure out how to incorporate real dates in the graph
        while (stringTokenizer.hasMoreTokens()) {

            long timeInMS = Long.parseLong(stringTokenizer.nextToken());
            double stockPrice = Double.parseDouble(stringTokenizer.nextToken());

//            Date date = new Date(timeInMS);

            entries.add(new Entry((float) (cnt++), (float) stockPrice));

        }

        return entries;

    }

}
