package com.mayank7319gmail.hospitallocator.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Mayank Gupta on 10-12-2017.
 */

@Parcel
public class PlaceList {

    @SerializedName("next_page_token")
    String nextPageToken;

    @SerializedName("results")
    public ArrayList<SinglePlace> places;

    public PlaceList(String nextPageToken, ArrayList<SinglePlace> places) {
        this.nextPageToken = nextPageToken;
        this.places = places;
    }

    public PlaceList(){}

}
