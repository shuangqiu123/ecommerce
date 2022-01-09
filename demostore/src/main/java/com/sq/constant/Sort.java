package com.sq.constant;

public enum Sort {
    NEWEST(0),
    PRICE_LOW_TO_HIGH(1),
    PRICE_HIGH_TO_LOW(2);

    private final int value;
    Sort(final int newValue) {
        value = newValue;
    }
    public int getValue() { return value; }
}
