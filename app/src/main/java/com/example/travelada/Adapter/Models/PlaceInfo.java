package com.example.travelada.Adapter.Models;

import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;

public class PlaceInfo {
    private String name;
    private String address;
    private String phoneNumber;
    private String id;
    private Uri websiteUri;
    private LatLng latlng;
    private float rating;
    private String attributions;

    public PlaceInfo(String address, String attributions, String id, LatLng latlng, String name, String phoneNumber, float rating, Uri websiteUri) {
        this.address = address;
        this.attributions = attributions;
        this.id = id;
        this.latlng = latlng;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
        this.websiteUri = websiteUri;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAttributions() {
        return attributions;
    }

    public void setAttributions(String attributions) {
        this.attributions = attributions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LatLng getLatlng() {
        return latlng;
    }

    public void setLatlng(LatLng latlng) {
        this.latlng = latlng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Uri getWebsiteUri() {
        return websiteUri;
    }

    public void setWebsiteUri(Uri websiteUri) {
        this.websiteUri = websiteUri;
    }

}


