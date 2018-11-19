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

    private int currDisplayedTranslationIndex;
    private int currDisplayedWordIndex;
    private int currScore;
    private Random random;

    public MainActivityPresenter(MainActivityRepository repository, MainActivityMvpView mvpView) {
        this.repository = repository;
        this.mvpView = mvpView;
        this.currScore = 0;
        this.random = new Random();
    }

    public void fetchWords(InputStream inputStream) {

        words = repository.fetchWords(inputStream);
        wordsToPlay = new ArrayList<>(words);

        fetchNewWord();

        try {
            mvpView.closeStream(inputStream);
        } catch (IOException e) {
            //TODO decide on error message or action
        }
    }

    public void fetchNewWord() {
        if (!wordsToPlay.isEmpty()) {
            currDisplayedTranslationIndex = generateNextInt();
            currDisplayedWordIndex = generateNextInt();
            mvpView.showWord(wordsToPlay.get(currDisplayedWordIndex).english,
                    wordsToPlay.get(currDisplayedTranslationIndex).spanish);
        } else {
            mvpView.showScore(currScore);
        }
    }

    public void checkIfCorrectAnswer(boolean correctChosen) {
        Word currWord = wordsToPlay.get(currDisplayedWordIndex);
        Word wordShownToUser = wordsToPlay.get(currDisplayedTranslationIndex);
        wordsToPlay.remove(currDisplayedWordIndex);

        if (currWord.spanish.equals(wordShownToUser.spanish) &&
                correctChosen || !currWord.spanish.equals(wordShownToUser.spanish) &&
                !correctChosen) {
            mvpView.showCorrectFeedback(++currScore);
        } else {
            mvpView.showWrongFeedback();
        }
    }

    public void noAnswerChosen() {
        wordsToPlay.remove(currDisplayedWordIndex);
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

    private int generateNextInt() {
        return random.nextInt(wordsToPlay.size());
    }

}
