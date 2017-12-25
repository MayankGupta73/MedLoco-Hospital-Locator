package com.mayank7319gmail.hospitallocator.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Mayank Gupta on 12-12-2017.
 */

@Parcel
public class Geometry {

    @SerializedName("location")
    Location location;

    public Geometry(Location location) {
        this.location = location;
    }

    public Geometry() {
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
