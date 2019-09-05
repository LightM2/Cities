package com.svitlanamozharovska.cities.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Single;


@Dao
public interface CityDao {

    @Query("SELECT * FROM cities")
    Single<List<City>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<City> cities);

}
