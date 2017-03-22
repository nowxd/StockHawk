package com.udacity.stockhawk.model;

public class HistoryData {

    private long timeInMS;
    private double stockPrice;

    public HistoryData(long timeMS, double stockPrice) {
        this.timeInMS = timeMS;
        this.stockPrice = stockPrice;
    }

    public long getTimeInMS() {
        return timeInMS;
    }

    public void setTimeInMS(long timeInMS) {
        this.timeInMS = timeInMS;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

}
