package com.udacity.stockhawk.ui;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class DateAxisValueFormatter implements IAxisValueFormatter {

    private String[] formattedDates;

    public DateAxisValueFormatter(String[] formatedDates) {
        this.formattedDates = formatedDates;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        int index = (int) value;
        return formattedDates[index];
    }

}
