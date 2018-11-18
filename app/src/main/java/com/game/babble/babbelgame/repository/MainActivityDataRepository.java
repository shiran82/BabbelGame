package com.game.babble.babbelgame.repository;

import com.game.babble.babbelgame.model.Word;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.Observable;

public class MainActivityDataRepository implements MainActivityRepository {

    @Override
    public List<Word> fetchWords(InputStream inputStream) {
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(inputStream);
        Type type = new com.google.gson.reflect.TypeToken<List<Word>>(){}.getType();
        List<Word> words = gson.fromJson(reader, type);

        return words;

//        Gson gson =new Gson();
//        Type type = new com.google.gson.reflect.TypeToken<TreeSet<Track>>(){}.getType();
//        return Observable.just(gson.fromJson(TRACK_RESPONSE, type));
    }

//    @Override
//    public Observable<Manufacturer> getManufacturer(int page, int pageSize, String apiKey) {
//        CarService apiService = APIClient.getClient().create(CarService.class);
//
//        return apiService.fetchManufacturer(page, pageSize, apiKey);
//    }
//
//    @Override
//    public Observable<CarType> getCarType(String manufacturer, int page, int pageSize, String apiKey) {
//        CarService apiService = APIClient.getClient().create(CarService.class);
//
//        return apiService.fetchMainCarTypes(manufacturer, page, pageSize, apiKey);
//    }
//
//    @Override
//    public Observable<CarBuildDate> getCarBuildDates(String manufacturer, String mainType, String apiKey) {
//        CarService apiService = APIClient.getClient().create(CarService.class);
//
//        return apiService.fetchBuildDates(manufacturer, mainType, apiKey);
//    }
//
//    @Override
//    public void saveNumOfPagesCarType(int totalPageCount) {
//        PreferenceStore.setNumOfPagesCarType(totalPageCount);
//    }
//
//    @Override
//    public void saveNumOfPagesManufacturer(int totalPageCount) {
//        PreferenceStore.setNumOfPagesManufacturer(totalPageCount);
//    }
//
//    @Override
//    public int getNumOfPagesCarType() {
//        return PreferenceStore.getNumOfPagesCarType();
//    }
//
//    @Override
//    public int getNumOfPagesManufacturer() {
//        return PreferenceStore.getNumOfPagesManufacturer();
//    }
}
