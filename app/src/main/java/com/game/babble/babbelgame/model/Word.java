package com.game.babble.babbelgame.model;

import com.google.gson.annotations.SerializedName;

public class Word {
    @SerializedName("text_eng")
    public final String english;
    @SerializedName("text_spa")
    public final String spanish;

    private boolean wordAlreadyDisplayed;

    public Word(String english, String spanish) {
        this.english = english;
        this.spanish = spanish;
        this.wordAlreadyDisplayed = false;
    }

    public boolean isWordAlreadyDisplayed() {
        return wordAlreadyDisplayed;
    }

    public void setWordAlreadyDisplayed(boolean wordAlreadyDisplayed) {
        this.wordAlreadyDisplayed = wordAlreadyDisplayed;
    }
}
