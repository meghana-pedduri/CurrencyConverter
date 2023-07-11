package com.example.currencyconverter;

public class OrderInfo {


    String currencyType;
    double amountOrdered;
    double amountPaid;

    public OrderInfo(){

    }

    public OrderInfo(String currencyType, double amountOrdered, double amountPaid) {

        this.currencyType = currencyType;
        this.amountOrdered = amountOrdered;
        this.amountPaid = amountPaid;
    }


    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public double getAmountOrdered() {
        return amountOrdered;
    }

    public void setAmountOrdered(double amountOrdered) {
        this.amountOrdered = amountOrdered;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }
}
