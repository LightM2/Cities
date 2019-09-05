package com.svitlanamozharovska.cities.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityName {
    @SerializedName("lng")
    @Expose
    private String lng;

    @SerializedName("geonameId")
    @Expose
    private String geonameId;

    @SerializedName("countryCode")
    @Expose
    private String countryCode;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("toponymName")
    @Expose
    private String toponymName;

    @SerializedName("lat")
    @Expose
    private String lat;

    @SerializedName("fcl")
    @Expose
    private String fcl;

    @SerializedName("fcode")
    @Expose
    private String fcode;

    public String getLng ()
    {
        return lng;
    }

    public void setLng (String lng)
    {
        this.lng = lng;
    }

    public String getGeonameId ()
    {
        return geonameId;
    }

    public void setGeonameId (String geonameId)
    {
        this.geonameId = geonameId;
    }

    public String getCountryCode ()
    {
        return countryCode;
    }

    public void setCountryCode (String countryCode)
    {
        this.countryCode = countryCode;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getToponymName ()
    {
        return toponymName;
    }

    public void setToponymName (String toponymName)
    {
        this.toponymName = toponymName;
    }

    public String getLat ()
    {
        return lat;
    }

    public void setLat (String lat)
    {
        this.lat = lat;
    }

    public String getFcl ()
    {
        return fcl;
    }

    public void setFcl (String fcl)
    {
        this.fcl = fcl;
    }

    public String getFcode ()
    {
        return fcode;
    }

    public void setFcode (String fcode)
    {
        this.fcode = fcode;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [lng = "+lng+", geonameId = "+geonameId+", countryCode = "+countryCode+", name = "+name+", toponymName = "+toponymName+", lat = "+lat+", fcl = "+fcl+", fcode = "+fcode+"]";
    }
}
