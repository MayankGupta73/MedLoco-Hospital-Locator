package com.mayank7319gmail.hospitallocator.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by Mayank Gupta on 12-12-2017.
 */

@Parcel
public class SinglePlace {
    String id;
    String name;
    String icon;

    @SerializedName("photos")
    ArrayList<Photo> photos;

    @SerializedName("place_id")
    String placeId;

    String vicinity;
    float rating;

    @SerializedName("geometry")
    Geometry geometry;

    LatLng loc;
    int distance;
    String distanceString;
    int timeMinutes;
    String timeString;

    public SinglePlace(String id, ArrayList<Photo> photos, String icon, String name, String vicinity, String placeId,
                       float rating, LatLng loc, int distance, String distanceString, int timeMinutes, String timeString, Geometry geometry) {
        this.id = id;
        this.photos = photos;
        this.icon = icon;
        this.name = name;
        this.vicinity = vicinity;
        this.placeId = placeId;
        this.rating = rating;
        this.loc = loc;
        this.distance = distance;
        this.distanceString = distanceString;
        this.timeMinutes = timeMinutes;
        this.timeString = timeString;
        this.geometry = geometry;
    }

    public String getDistanceString() {
        return distanceString;
    }

    public void setDistanceString(String distanceString) {
        this.distanceString = distanceString;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getTimeMinutes() {
        return timeMinutes;
    }

    public void setTimeMinutes(int timeMinutes) {
        this.timeMinutes = timeMinutes;
    }

    public SinglePlace() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LatLng getLoc() {
        if(loc == null)
        loc = new LatLng(geometry.location.latitude,geometry.location.longitude);

        return loc;
    }

    public void setLoc(LatLng loc) {
        this.loc = loc;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
