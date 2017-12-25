package com.mayank7319gmail.hospitallocator.models;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Mayank Gupta on 19-12-2017.
 */

@Parcel
public class ElementsArray {
    ArrayList<DistanceDuration> elements;

    public ElementsArray() {
    }

    public ArrayList<DistanceDuration> getElements() {
        return elements;
    }

    public void setElements(ArrayList<DistanceDuration> elements) {
        this.elements = elements;
    }
}
