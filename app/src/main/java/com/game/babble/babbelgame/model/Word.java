package com.game.babble.babbelgame.model;

import com.google.gson.annotations.SerializedName;

public class Word {
    @SerializedName("text_eng")
    public final String english;
    @SerializedName("text_spa")
    public final String spanish;

    public Word(String english, String spanish) {
        this.english = english;
        this.spanish = spanish;
    }
}
