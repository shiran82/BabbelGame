package com.game.babble.babbelgame.screen;

import com.game.babble.babbelgame.model.Word;

public interface MainActivityMvpView {

    void showWord(Word word);

    void showWord(String english, String spanish);

    void showCorrectFeedback();

    void showWrongFeedback();
}
