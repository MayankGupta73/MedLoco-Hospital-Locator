package com.mayank7319gmail.hospitallocator.models;

import org.parceler.Parcel;

/**
 * Created by Mayank Gupta on 19-12-2017.
 */

@Parcel
public class DistanceDuration {
    ValuePair distance;
    ValuePair duration;

    String status;

    public DistanceDuration() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ValuePair getDistance() {

        return distance;
    }

    public void setDistance(ValuePair distance) {
        this.distance = distance;
    }

    public ValuePair getDuration() {
        return duration;
    }

    public void setDuration(ValuePair duration) {
        this.duration = duration;
    }
}
