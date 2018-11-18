package com.game.babble.babbelgame.repository;


import com.game.babble.babbelgame.model.Word;

import java.io.InputStream;
import java.util.List;

import io.reactivex.Observable;

public interface MainActivityRepository {
    List<Word> fetchWords(InputStream inputStream);

//    Observable<Manufacturer> getManufacturer(int page, int pageSize, String apiKey);
//
//    Observable<CarType> getCarType(String manufacturer, int page, int pageSize, String apiKey);
//
//    Observable<CarBuildDate> getCarBuildDates(String manufacturer, String main_type, String apiKey);
//
//    void saveNumOfPagesCarType(int totalPageCount);
//
//    void saveNumOfPagesManufacturer(int totalPageCount);
//
//    int getNumOfPagesCarType();
//
//    int getNumOfPagesManufacturer();
}