package com.svitlanamozharovska.cities.DB;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


import com.svitlanamozharovska.cities.Converters;

@Database(entities = {City.class},version = 1)
@TypeConverters({Converters.class})
public abstract class CityDatabase extends RoomDatabase {

    public abstract CityDao cityDao();

}
