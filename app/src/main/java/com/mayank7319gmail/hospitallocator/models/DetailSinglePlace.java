package com.mayank7319gmail.hospitallocator.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Mayank Gupta on 23-12-2017.
 */

public class DetailSinglePlace{

    public class OpeningHours{
        @SerializedName("weekday_text")
        ArrayList<String> weekday;

        public OpeningHours() {
        }

        public ArrayList<String> getWeekday() {
            return weekday;
        }

        public void setWeekday(ArrayList<String> weekday) {
            this.weekday = weekday;
        }
    }

    private String id;
    private String name;
    private String icon;

    @SerializedName("photos")
    private ArrayList<Photo> photos;

    @SerializedName("place_id")
    private String placeId;

    private String vicinity;
    private float rating;

    @SerializedName("formatted_address")
    String address;

    @SerializedName("formatted_phone_number")
    String phone;

    @SerializedName("opening_hours")
    OpeningHours openingHours;

    String url;
    String website;

    public DetailSinglePlace() {
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
