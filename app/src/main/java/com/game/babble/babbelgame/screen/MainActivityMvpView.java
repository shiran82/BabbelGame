package com.game.babble.babbelgame.screen;

import java.io.IOException;
import java.io.InputStream;

public interface MainActivityMvpView {

    void showWord(String english, String spanish);

    void showCorrectFeedback(int currScore);

    void showWrongFeedback();

    void showScore(int currScore);

    void closeStream(InputStream inputStream) throws IOException;
}
