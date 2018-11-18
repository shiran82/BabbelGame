package com.game.babble.babbelgame.screen;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.game.babble.babbelgame.R;
import com.game.babble.babbelgame.model.Word;
import com.game.babble.babbelgame.presenter.MainActivityPresenter;
import com.game.babble.babbelgame.repository.MainActivityDataRepository;
import com.game.babble.babbelgame.databinding.ActivityMainBinding;

import java.io.InputStream;

public class MainActivity extends Activity implements MainActivityMvpView {
    private ActivityMainBinding binding;
    private InputStream inputStream;
    private MainActivityPresenter presenter;
    private AnimationSet set;

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
                binding.buttonWrong.setClickable(false);
                binding.buttonCorrect.setClickable(false);
                binding.textViewWordTranslation.setVisibility(View.GONE);
                presenter.checkIfCorrectAnswer(true);
            }
        });

        binding.buttonWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.buttonWrong.setClickable(false);
                binding.buttonCorrect.setClickable(false);
                binding.textViewWordTranslation.setVisibility(View.GONE);
                presenter.checkIfCorrectAnswer(false);

            }
        });

        binding.toolbar.setTitle("score");

        presenter.fetchWords(inputStream);

    }

    @Override
    public void showWord(Word word) {
//        binding.textViewWordTranslation.setText(word.english);
//        binding.textViewWordTranslation.startAnimation(set);

    }

    @Override
    public void showWord(final String english, final String spanish) {
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
//        setAnimation();
//        binding.textViewWordTranslation.startAnimation(set);
    }

    @Override
    public void showCorrectFeedback() {
        binding.getRoot().setBackground(ContextCompat.getDrawable(this, R.color.colorGreen));
        binding.textViewWordTranslation.getAnimation().setAnimationListener(null);
        binding.textViewWordTranslation.clearAnimation();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.fetchNewWord();
            }
        }, 1500);
    }

    @Override
    public void showWrongFeedback() {
        binding.getRoot().setBackground(ContextCompat.getDrawable(this, R.color.colorRed));
        binding.textViewWordTranslation.getAnimation().setAnimationListener(null);
        binding.textViewWordTranslation.clearAnimation();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.fetchNewWord();
            }
        }, 1500);

    }

    public void setAnimation() {

        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        set = new AnimationSet(true);

        RelativeLayout.LayoutParams layout = (RelativeLayout.LayoutParams) binding.textViewWordTranslation.getLayoutParams();

//        Resources r = getResources();
//        float px = TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                binding.buttonCorrect.get,
//                r.getDisplayMetrics()
//        );

        binding.buttonCorrect.getHeight();
        Rect rectangle = new Rect();
        Window window = getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        Animation animT = new TranslateAnimation(0f, 0f, 0f,
                size.y - binding.buttonCorrect.getHeight() - layout.topMargin -
                        statusBarHeight);

        set.addAnimation(animT);
        set.setDuration(13000);
        set.setRepeatCount(0);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.textViewWordTranslation.setVisibility(View.VISIBLE);
                binding.buttonWrong.setClickable(true);
                binding.buttonCorrect.setClickable(true);
                binding.getRoot().setBackground(ContextCompat.getDrawable(getApplicationContext(), R.color.colorWhite));
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.buttonWrong.setClickable(false);
                binding.buttonCorrect.setClickable(false);

                presenter.noAnswerChosen();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
