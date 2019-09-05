package com.svitlanamozharovska.cities;

import androidx.room.TypeConverter;

import java.util.ArrayList;
import java.util.Arrays;

public class Converters {

    @TypeConverter
    public String fromCityList(ArrayList<String> cityList){
        return cityList.toString();
    }

    @TypeConverter
    public ArrayList<String> toCityList(String data){
        data = data.substring(1, data.length() - 1);
        return (ArrayList<String>) Arrays.asList(data.split(", "));
    }
}
