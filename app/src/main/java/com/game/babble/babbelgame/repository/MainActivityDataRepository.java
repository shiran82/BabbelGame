package com.game.babble.babbelgame.repository;

import com.game.babble.babbelgame.model.Word;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;

public class MainActivityDataRepository implements MainActivityRepository {

    @Override
    public List<Word> fetchWords(InputStream inputStream) {
        Gson gson = new Gson();
        Reader reader = new InputStreamReader(inputStream);
        Type type = new com.google.gson.reflect.TypeToken<List<Word>>() {
        }.getType();

        return gson.fromJson(reader, type);
    }
}
