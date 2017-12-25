package com.mayank7319gmail.hospitallocator.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Mayank Gupta on 19-12-2017.
 */

@Parcel
public class DistanceResult {
    String status;

    @SerializedName("origin_addresses")
    ArrayList<String> originAddresses;
    @SerializedName("destination_addresses")
    ArrayList<String> destinationAddresses;

    ArrayList<ElementsArray> rows;

    public DistanceResult() {
    }

    public DistanceResult(String status, ArrayList<ElementsArray> rows, ArrayList<String> destinationAddresses, ArrayList<String> originAddresses) {

        this.status = status;
        this.rows = rows;
        this.destinationAddresses = destinationAddresses;
        this.originAddresses = originAddresses;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<String> getDestinationAddresses() {
        return destinationAddresses;
    }

    public void setDestinationAddresses(ArrayList<String> destinationAddresses) {
        this.destinationAddresses = destinationAddresses;
    }

    public ArrayList<String> getOriginAddresses() {
        return originAddresses;
    }

    public void setOriginAddresses(ArrayList<String> originAddresses) {
        this.originAddresses = originAddresses;
    }

    public ArrayList<ElementsArray> getRows() {
        return rows;
    }

    public void setRows(ArrayList<ElementsArray> rows) {
        this.rows = rows;
    }
}
