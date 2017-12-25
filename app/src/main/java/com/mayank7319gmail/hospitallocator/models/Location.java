package com.mayank7319gmail.hospitallocator.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Mayank Gupta on 21-12-2017.
 */

@Parcel
public class Location{
    @SerializedName("lat")
    Double latitude;

    @SerializedName("lng")
    Double longitude;

    public Location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location() {
    }
}
