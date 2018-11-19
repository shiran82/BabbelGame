package com.game.babble.babbelgame.repository;


import com.game.babble.babbelgame.model.Word;

import java.io.InputStream;
import java.util.List;

public interface MainActivityRepository {
    List<Word> fetchWords(InputStream inputStream);
}