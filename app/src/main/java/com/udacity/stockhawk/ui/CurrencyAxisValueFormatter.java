package com.udacity.stockhawk.ui;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;

public class CurrencyAxisValueFormatter implements IAxisValueFormatter {

    private DecimalFormat decimalFormat;

    public CurrencyAxisValueFormatter() {
        decimalFormat = new DecimalFormat("'$'0.00");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return decimalFormat.format(value);
    }

}
