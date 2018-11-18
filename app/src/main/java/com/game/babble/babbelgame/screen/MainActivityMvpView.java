package com.game.babble.babbelgame.screen;

public interface MainActivityMvpView {

    void showWord(String english, String spanish);

    void showCorrectFeedback(int currScore);

    void showWrongFeedback();

    void showScore(int currScore);
}
