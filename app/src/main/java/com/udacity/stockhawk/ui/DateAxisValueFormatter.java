package com.udacity.stockhawk.ui;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.udacity.stockhawk.model.HistoryData;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DateAxisValueFormatter implements IAxisValueFormatter {

    private String[] formattedDates;

    public DateAxisValueFormatter(List<HistoryData> historyData) {
        this.formattedDates = parseDates(historyData);
    }

    /**
     * Converts the timeInMS to a date string that will be displayed in the chart
     */
    private String[] parseDates(List<HistoryData> dataList) {

        int len = dataList.size();
        String[] dates = new String[len];

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM-yy", Locale.US);

        for (int index = 0; index < len; index++) {

            calendar.setTimeInMillis(dataList.get(index).getTimeInMS());
            dates[index] = dateFormat.format(calendar.getTime());

        }

        return dates;

    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int index = (int) value;
        return formattedDates[index];
    }

}
