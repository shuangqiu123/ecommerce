package com.sq.constant;

public enum Category {
    NEW_IN(0),
    POPULAR(1);

    private final int value;
    Category(final int newValue) {
        value = newValue;
    }
    public int getValue() { return value; }
}
