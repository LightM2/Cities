package com.svitlanamozharovska.cities.Retrofit;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CityService {

    @GET("searchJSON?")
    Observable<CityData> queryCityName(@Query("username") String username, @Query("country") String country,
                                           @Query("maxRows") int maxRows, @Query("style") String style);

}
