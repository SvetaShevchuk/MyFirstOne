package com.kidsPlayerPrincess.kidsplayerprincess;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class ActivitySplash extends AppCompatActivity {
    public InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        loadAds();

//        MobileAds.initialize(this, "ca-app-pub-5450818110715757~2796444339");
//        interstitialAd = new InterstitialAd(this);
//        interstitialAd.setAdUnitId("ca-app-pub-5450818110715757/8339882225");
//        AdRequest adRequest = new AdRequest.Builder().build();
//        interstitialAd.loadAd(adRequest);
//
//        interstitialAd.setAdListener(new AdListener(){
//            @Override
//            public void onAdClosed() {
//                try{
//                   Intent intentSplash = new Intent(ActivitySplash.this, MainActivity.class);
//                   startActivity(intentSplash);
//                   finish();
//                }catch(Exception e){
//
//                }
//            }
//        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(ActivitySplash.this);
                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            Intent intentSplash = new Intent(ActivitySplash.this,
                                    MainActivity.class);
                            startActivity(intentSplash);
                            finish();
                        }
                    });
                } else {
                    //Toast.makeText(getApplicationContext(), "Not loaded", Toast.LENGTH_SHORT).show();
                    Intent intentSplash = new Intent(ActivitySplash.this,
                            MainActivity.class);
                    startActivity(intentSplash);
                    finish();
                }

            }
        }, 800);
    }

    public void loadAds(){
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-5450818110715757/8339882225", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                        Log.i("TAG", "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i("TAG", loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
    }
}