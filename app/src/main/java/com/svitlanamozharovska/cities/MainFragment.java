package com.svitlanamozharovska.cities;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.svitlanamozharovska.cities.DB.City;
import com.svitlanamozharovska.cities.DB.CityDatabase;
import com.svitlanamozharovska.cities.Retrofit.CityData;
import com.svitlanamozharovska.cities.Retrofit.CityName;
import com.svitlanamozharovska.cities.Retrofit.CityService;
import com.svitlanamozharovska.cities.Retrofit.GeoNamesApi;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainFragment extends Fragment {

    private static Retrofit retrofit = null;

    private RVAdapter adapter;

    private ArrayList<String> cityNamesTest = new ArrayList<>();

    private ArrayList<String> autoCompleteCities = new ArrayList<>();

    private CityDatabase db;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment,
                container, false);

        return view;
    }


    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = Room.databaseBuilder(view.getContext(), CityDatabase.class, "cities").build();

        readingFromDB();

        final AutoCompleteTextView newCityName = view.findViewById(R.id.cities_tv);
        final ArrayAdapter<String> tvAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_dropdown_item_1line, autoCompleteCities);
        newCityName.setAdapter(tvAdapter);

        retrofit = GeoNamesApi.getClient(view.getContext());

        callEndpoints();

        RecyclerView recyclerView = view.findViewById(R.id.cities_list_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new RVAdapter(this.getContext(), cityNamesTest);
        recyclerView.setAdapter(adapter);

        newCityName.setOnItemClickListener((adapterView, view12, i, l) -> {
            Log.d("testR", String.valueOf(i));

            if (!cityNamesTest.contains(tvAdapter.getItem(i))) {
                cityNamesTest.add(tvAdapter.getItem(i));
                adapter.notifyDataSetChanged();

                ArrayList<City> cities = new ArrayList<>();
                City city = new City();
                city.setCityList(cityNamesTest.get(cityNamesTest.size()-1));
                cities.add(city);
                writingToDB(cities);

            }
        });

        final EditText editText = (EditText) view.findViewById(R.id.filter_et);
        editText.setOnKeyListener((view1, i, keyEvent) -> {
                    if (keyEvent.getAction() == KeyEvent.ACTION_DOWN &&
                            (i == KeyEvent.KEYCODE_ENTER)) {
                        Log.d("testR", editText.getText().toString());
                        ArrayList<String> cityName = sortedCities(editText.getText().toString());
                        cityNamesTest.clear();
                        cityNamesTest.addAll(cityName);
                        adapter.notifyDataSetChanged();
                        return true;
                    }

                    return false;

                }
        );

    }

    @Override
    public void onPause() {

        super.onPause();
    }


    private void callEndpoints() {
        CityService cityService = retrofit.create(CityService.class);

        Observable<CityData> cryptoObservable = cityService.queryCityName("ksuhiyp", "ua", 1000, "SHORT");
        cryptoObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CityData>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("testR", "onSubscribe");


                    }

                    @Override
                    public void onNext(CityData cityData) {
                        Log.d("testR", "onNext");
                        Log.d("testR", cityData.getGeonames().toString());
                        ArrayList<CityName> cityNameArrayList = cityData.getGeonames();
                        for (int i = 0; i < cityNameArrayList.size(); i++) {
                            if (!autoCompleteCities.contains(cityNameArrayList.get(i).getName())) {
                                autoCompleteCities.add(cityNameArrayList.get(i).getName());
                            }
                        }

                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.d("testR", e.toString());
                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        Log.d("testR", "onComplete");
                    }
                });
    }


    private ArrayList<String> sortedCities(String string) {
        ArrayList<String> newCityList = new ArrayList<>();
        for (String cityName : cityNamesTest) {
            if (string.equals(cityName)) {
                newCityList.add(cityName);
            }
        }

        return newCityList;
    }

    private void readingFromDB() {
        Single<List<City>> single = db.cityDao().getAll();
        single.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<City>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("testR", "onSubscribe2");
                        // add it to a CompositeDisposable
                    }

                    @Override
                    public void onSuccess(List<City> cities) {
                        Log.d("testR", "read2");
                        for (int i = 0; i < cities.size(); i++) {
                            cityNamesTest.add(cities.get(i).getCityList());
                            Log.d("testR", "read");

                        }
                        adapter.notifyDataSetChanged();

                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.d("testR", String.valueOf(e));
                    }
                });
    }

    private void writingToDB(List<City> city) {

        Completable.fromAction(() -> db.cityDao().insertAll(city))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("testR", "add");
                        Log.d("testR", String.valueOf(city.isEmpty()));

                    }

                    @Override
                    public void onComplete() {

                        // action was completed successfully
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("testR", String.valueOf(e));
                    }
                });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }
}


