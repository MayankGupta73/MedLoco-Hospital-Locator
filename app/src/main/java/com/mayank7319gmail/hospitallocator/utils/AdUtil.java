package com.mayank7319gmail.hospitallocator.utils;

import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by Mayank Gupta on 15-12-2017.
 */

public class AdUtil {
    final static String admobAppId = "ca-app-pub-5628554689532375~4974727410";
    final static String testId = "ca-app-pub-3940256099942544~3347511713";

    AdView mAdView;

    public static void initAds(Context ctx){
        MobileAds.initialize(ctx,admobAppId);
    }

    public static void loadAds(AdView mAdView){
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
}
