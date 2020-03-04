package com.kalvinkao.bombtimerforcsgo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class MainActivity extends AppCompatActivity {
    private AdView mAdView;


    public CountDownTimer countDownTimer;
    private final long interval = 100;
    int secondsLeft = 0;
    /* access modifiers changed from: private */
    public Button startB;
    private final long startTime = 40000;
    public TextView text;
    /* access modifiers changed from: private */
    public boolean timerHasStarted = false;


    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @SuppressLint("SetTextI18n")
        public void onTick(long ms) {
            if (Math.round(((float) ms) / 1000.0f) != secondsLeft) {
                secondsLeft = Math.round(((float) ms) / 1000.0f);
                text.setText(BuildConfig.FLAVOR + secondsLeft);
            }
            Log.i("test", "ms=" + ms + " till finished=" + secondsLeft);
        }

        @SuppressLint("SetTextI18n")
        public void onFinish() {
            text.setText("0");
            startB.setText("RESTART");
            countDownTimer.cancel();
            timerHasStarted = false;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startB = (Button) findViewById(R.id.button);
        startB.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (!timerHasStarted) {
                    countDownTimer.start();
                    timerHasStarted = true;
                    startB.setText("STOP");
                    return;
                }
                countDownTimer.cancel();
                timerHasStarted = false;
                startB.setText("RESTART");
            }
        });

        text = (TextView) findViewById(R.id.bombtime);
        countDownTimer = new MyCountDownTimer(startTime, 100);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }
}




