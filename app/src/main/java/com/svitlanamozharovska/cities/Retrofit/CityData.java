package com.svitlanamozharovska.cities.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CityData {
    @SerializedName("totalResultsCount")
    @Expose
    private int totalResultsCount;

    @SerializedName("geonames")
    @Expose
    private ArrayList<CityName> geonames;

    public ArrayList<CityName> getGeonames() {
        return geonames;
    }

    public void setGeonames(ArrayList<CityName> geonames) {
        this.geonames = geonames;
    }
}
