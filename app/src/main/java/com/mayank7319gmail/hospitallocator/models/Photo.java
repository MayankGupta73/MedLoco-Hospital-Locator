package com.mayank7319gmail.hospitallocator.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Mayank Gupta on 12-12-2017.
 */

@Parcel
public class Photo {
    int height, width;

    @SerializedName("photo_reference")
    String photoReference;

    public Photo() {
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getPhotoReference() {
        return photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
