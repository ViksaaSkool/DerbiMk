package com.derbi.mk.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.badoo.mobile.util.WeakHandler;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.derbi.mk.R;
import com.derbi.mk.helpers.ChangeActivityHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by varsovski on 27-May-15.
 */
public class SplashScreenActivity extends BaseActivity {

    @InjectView(R.id.imgSplashLogo)
    ImageView mImgSplashLogo;
    private WeakHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ButterKnife.inject(this);

        mHandler = new WeakHandler();
        startAnimation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startApp();
    }


    public void startApp() {
        final BaseActivity ba = this;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ChangeActivityHelper.changeActivity(ba, MainActivity.class, true);
            }
        }, 2500);

    }

    public void startAnimation() {

        YoYo.with(Techniques.FadeIn)
                .duration(2000)
                .playOn(mImgSplashLogo);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.reset(this);
    }
}
