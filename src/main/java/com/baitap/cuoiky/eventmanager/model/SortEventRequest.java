package com.baitap.cuoiky.eventmanager.model;

public class SortEventRequest {
    private int year;
    private String attribute;
    private boolean reverse;

    public SortEventRequest() {
       }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public boolean isReverse() {
        return reverse;
    }

    public void setReverse(boolean reverse) {
        this.reverse = reverse;
    }
}
