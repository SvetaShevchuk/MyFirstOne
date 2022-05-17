package com.myDictionaryForLearning.dictionary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.myDictionaryForLearning.dictionary.green.MainActivityGreen;
import com.myDictionaryForLearning.dictionary.grey.MainActivityGrey;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class ActivitySplash extends AppCompatActivity {
    TextView tv_text_choose,  tv_text_blue, tv_text_start, tv_text_grey, tv_text_green;
    ImageView image_blue,  image_grey, image_green;
    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this,"ca-app-pub-5450818110715757/2090232287", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });
        initViews();


        image_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mInterstitialAd != null) {
                    mInterstitialAd.show(ActivitySplash.this);
                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            Intent intentGreen = new Intent(ActivitySplash.this, MainActivity.class);
                            startActivity(intentGreen);
                        }
                    });
                } else {
                    Intent intent = new Intent(ActivitySplash.this, MainActivity.class);
                    startActivity(intent);
                }




            }
        });
        image_grey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivitySplash.this, MainActivityGrey.class);
                startActivity(intent);
            }
        });
        image_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivitySplash.this, MainActivityGreen.class);
                startActivity(intent);
            }
        });
    }


    private void initViews() {
        tv_text_choose = findViewById(R.id.tv_text_choose);
        image_blue = findViewById(R.id.image_blue);
        image_green = findViewById(R.id.image_green);
        tv_text_blue = findViewById(R.id.tv_text_blue);
        tv_text_start = findViewById(R.id.tv_text_start);
        tv_text_grey = findViewById(R.id.tv_text_grey);
        tv_text_green = findViewById(R.id.tv_text_green);
        image_grey = findViewById(R.id.image_grey);

    }



}