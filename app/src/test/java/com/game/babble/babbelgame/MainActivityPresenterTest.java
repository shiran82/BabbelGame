package com.game.babble.babbelgame;

import com.game.babble.babbelgame.model.Word;
import com.game.babble.babbelgame.presenter.MainActivityPresenter;
import com.game.babble.babbelgame.screen.MainActivityMvpView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {
    @Mock
    private MainActivityMvpView mockMvpView;

    @InjectMocks
    private MainActivityPresenter presenter;

    @Spy
    private List<Word> wordsToPlay;
    private Word wordShownToUser;
    private Word currWord;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRequestNextWordEmptyList() {

        when(wordsToPlay.isEmpty()).thenReturn(true);
        presenter.fetchNewWord();

        verify(mockMvpView).showScore(anyInt());
        verify(mockMvpView, never()).showWord(anyString(), anyString());

    }

    @Test
    public void testRequestNextWordNonEmptyList() {

        Mockito.when(wordsToPlay.size()).thenReturn(100);
        when(wordsToPlay.isEmpty()).thenReturn(false);

        Word word = new Word("", "");

        when(wordsToPlay.get(anyInt())).thenReturn(word);
        presenter.fetchNewWord();


        verify(mockMvpView).showWord(anyString(), anyString());
        verify(mockMvpView, never()).showScore(anyInt());

    }

    @Test
    public void testCheckIfCorrectAnswerWrongFeedback() {

        Word word = new Word("", "");

        when(wordsToPlay.get(anyInt())).thenReturn(word);
        presenter.checkIfCorrectAnswer(false);


        verify(mockMvpView).showWrongFeedback();
        verify(mockMvpView, never()).showCorrectFeedback(anyInt());
    }

    @Test
    public void testCheckIfCorrectAnswerCorrectFeedback() {
        Word word = new Word("", "");

        when(wordsToPlay.get(anyInt())).thenReturn(word);
        presenter.checkIfCorrectAnswer(true);


        verify(mockMvpView).showCorrectFeedback(anyInt());
        verify(mockMvpView, never()).showWrongFeedback();
    }

    @Test
    public void testNoAnswerChosen() {
        presenter.noAnswerChosen();

        verify(mockMvpView).showWrongFeedback();
    }
}