package com.game.babble.babbelgame.screen;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.game.babble.babbelgame.R;
import com.game.babble.babbelgame.presenter.MainActivityPresenter;
import com.game.babble.babbelgame.repository.MainActivityDataRepository;
import com.game.babble.babbelgame.databinding.ActivityMainBinding;

import java.io.InputStream;

public class MainActivity extends Activity implements MainActivityMvpView {
    private ActivityMainBinding binding;
    private InputStream inputStream;
    private MainActivityPresenter presenter;
    private AnimationSet set;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        presenter = new MainActivityPresenter(new
                MainActivityDataRepository(), this);

        inputStream = getResources().openRawResource(R.raw.words);

        binding.buttonCorrect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnAnswerButtonClicked(true);
            }
        });

        binding.buttonWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setOnAnswerButtonClicked(false);

            }
        });

        binding.toolbar.setTitle(getString(R.string.score, 0));

        presenter.fetchWords(inputStream);

    }

    @Override
    public void showWord(final String english, final String spanish) {
        binding.linearLayout.setVisibility(View.VISIBLE);
        binding.buttonReplay.setVisibility(View.GONE);
        binding.textViewWordTranslation.setVisibility(View.VISIBLE);
        binding.textViewWord.setText(english);
        binding.textViewWordTranslation.setText(spanish);

        binding.relativeLayout.post(new Runnable() {
            @Override
            public void run() {
                setAnimation();
                binding.textViewWordTranslation.startAnimation(set);
            }
        });
    }

    @Override
    public void showCorrectFeedback(int currScore) {
        showFeedback(R.color.colorGreen);
        binding.toolbar.setTitle(getString(R.string.score, currScore));

        fetchNextWord();
    }

    @Override
    public void showWrongFeedback() {
        showFeedback(R.color.colorRed);

        fetchNextWord();

    }

    @Override
    public void showScore(int currScore) {
        binding.getRoot().setBackground(ContextCompat.getDrawable(this, R.color.colorWhite));
        binding.textViewWord.setText(getString(R.string.final_score, currScore));
        binding.textViewWordTranslation.setVisibility(View.GONE);
        binding.buttonReplay.setVisibility(View.VISIBLE);
        binding.linearLayout.setVisibility(View.GONE);
        binding.toolbar.setTitle("");

        binding.buttonReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.replayGame();
            }
        });
    }

    public void setAnimation() {

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        Window window = getWindow();
        Rect rectangle = new Rect();

        display.getSize(size);

        set = new AnimationSet(true);

        RelativeLayout.LayoutParams layout = (RelativeLayout.LayoutParams) binding.textViewWordTranslation.getLayoutParams();

        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        Animation animT = new TranslateAnimation(0f, 0f, 0f,
                size.y - binding.buttonCorrect.getHeight() - layout.topMargin -
                        statusBarHeight);

        set.addAnimation(animT);
        set.setDuration(presenter.getDuration());
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                setAnswerButtonClickable(true);
                binding.getRoot().setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.colorWhite));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                setAnswerButtonClickable(false);
                binding.textViewWordTranslation.setVisibility(View.GONE);

                presenter.noAnswerChosen();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }

    private void setAnswerButtonClickable(boolean clickable) {
        binding.buttonWrong.setClickable(clickable);
        binding.buttonCorrect.setClickable(clickable);
    }

    private void setOnAnswerButtonClicked(boolean answerChosen) {
        setAnswerButtonClickable(false);
        binding.textViewWordTranslation.setVisibility(View.GONE);
        presenter.checkIfCorrectAnswer(answerChosen);
    }

    private void fetchNextWord() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.fetchNewWord();
            }
        }, presenter.getDelay());
    }

    private void showFeedback(int color) {
        binding.getRoot().setBackground(ContextCompat.getDrawable(this, color));
        binding.textViewWordTranslation.getAnimation().setAnimationListener(null);
        binding.textViewWordTranslation.clearAnimation();
    }
}
