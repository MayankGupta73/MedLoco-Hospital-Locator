package com.mayank7319gmail.hospitallocator.models;

import org.parceler.Parcel;

/**
 * Created by Mayank Gupta on 19-12-2017.
 */

@Parcel
public class ValuePair {
    String text;
    int value;

    public ValuePair(String text, int value) {
        this.text = text;
        this.value = value;
    }

    public ValuePair() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
