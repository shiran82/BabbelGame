package com.game.babble.babbelgame.model;

public class Score {
    private int numOfCorrectWords;
    private int numOfWrongWords;
    private int totalWords;

    public int getNumOfCorrectWords() {
        return numOfCorrectWords;
    }

    public void setNumOfCorrectWords(int numOfCorrectWords) {
        this.numOfCorrectWords = numOfCorrectWords;
    }

    public int getNumOfWrongWords() {
        return numOfWrongWords;
    }

    public void setNumOfWrongWords(int numOfWrongWords) {
        this.numOfWrongWords = numOfWrongWords;
    }

    public int getTotalWords() {
        return totalWords;
    }

    public void setTotalWords(int totalWords) {
        this.totalWords = totalWords;
    }
}
