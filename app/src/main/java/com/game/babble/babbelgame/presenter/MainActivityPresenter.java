package com.game.babble.babbelgame.presenter;

import com.game.babble.babbelgame.model.Word;
import com.game.babble.babbelgame.repository.MainActivityRepository;
import com.game.babble.babbelgame.screen.MainActivityMvpView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivityPresenter {
    private final static int DURATION = 13000;
    private final static int DELAY = 1500;

    private MainActivityRepository repository;
    private MainActivityMvpView mvpView;
    private List<Word> wordsToPlay;
    private List<Word> words;
    private int currIndex;
    private int currScore;

    public MainActivityPresenter(MainActivityRepository repository, MainActivityMvpView mvpView) {
        this.repository = repository;
        this.mvpView = mvpView;
        this.currScore = 0;
    }

    public void fetchWords(InputStream inputStream) {

        words = repository.fetchWords(inputStream);
        wordsToPlay = new ArrayList<>(words);

        fetchNewWord();

        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void fetchNewWord() {
        if (wordsToPlay.size() > 0) {
            currIndex = generateNextWord(wordsToPlay);
            mvpView.showWord(wordsToPlay.get(0).english, wordsToPlay.get(currIndex).spanish);
        } else {
            mvpView.showScore(currScore);
            //restart game
        }
    }

    public void checkIfCorrectAnswer(boolean correctChosen) {
        Word currWord = wordsToPlay.get(0);
        Word wordShownToUser = wordsToPlay.get(currIndex);
        wordsToPlay.remove(0);
        if (currWord.spanish.equals(wordShownToUser.spanish) &&
                correctChosen || !currWord.spanish.equals(wordShownToUser.spanish) &&
                !correctChosen) {
            mvpView.showCorrectFeedback(++currScore);
        } else {
            mvpView.showWrongFeedback();
        }
    }

    public void noAnswerChosen() {
        wordsToPlay.remove(0);
        mvpView.showWrongFeedback();
    }

    public void replayGame() {
        wordsToPlay = new ArrayList<>(words);
        currScore = 0;

        fetchNewWord();
    }

    public int getDuration() {
        return DURATION;
    }

    public int getDelay() {
        return DELAY;
    }

    private int generateNextWord(List<Word> words) {

        return (int) (new Random().nextFloat() * (words.size() - 1));
    }

}
