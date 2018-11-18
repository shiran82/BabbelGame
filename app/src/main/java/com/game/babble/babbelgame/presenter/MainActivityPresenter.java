package com.game.babble.babbelgame.presenter;

import android.util.Log;

import com.game.babble.babbelgame.model.Word;
import com.game.babble.babbelgame.repository.MainActivityRepository;
import com.game.babble.babbelgame.screen.MainActivityMvpView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

public class MainActivityPresenter {
    private MainActivityRepository repository;
    private MainActivityMvpView mvpView;
    private List<Word> words;
    private int currIndex;

    public MainActivityPresenter(MainActivityRepository repository, MainActivityMvpView mvpView) {
        this.repository = repository;
        this.mvpView = mvpView;
    }

    public void fetchWords(InputStream inputStream) {

        words = repository.fetchWords(inputStream);

        fetchNewWord();

        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private int generateNextWord(List<Word> words) {

        return 0 + (int) (new Random().nextFloat() * (words.size() - 1 - 0));
    }

    public void fetchNewWord() {
        if (words.size() > 0) {
            currIndex = generateNextWord(words);
            mvpView.showWord(words.get(0).english, words.get(currIndex).spanish);
        } else {
            //restart game
        }
    }

    public void checkIfCorrectAnswer(boolean correctChosen) {
        Word word = words.get(0);
        words.remove(0);
        if (word.spanish.equals(words.get(currIndex).spanish) &&
                correctChosen || !word.spanish.equals(words.get(currIndex).spanish) && !correctChosen) {
            {
                mvpView.showCorrectFeedback();
//                mvpView.updateScore();
            }
        } else {
            mvpView.showWrongFeedback();
            //mvpView.showWrongScreen();
        }
    }

    public void noAnswerChosen() {
        words.remove(0);
        mvpView.showWrongFeedback();
    }
}
