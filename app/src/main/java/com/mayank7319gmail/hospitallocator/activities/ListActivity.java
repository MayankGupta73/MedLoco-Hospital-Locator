package com.mayank7319gmail.hospitallocator.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mayank7319gmail.hospitallocator.R;
import com.mayank7319gmail.hospitallocator.models.SinglePlace;
import com.mayank7319gmail.hospitallocator.recycler.HospitalListRecycler;
import com.mayank7319gmail.hospitallocator.utils.AdUtil;

import org.parceler.Parcels;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerHospital;
    ArrayList<SinglePlace> itemList;
    AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Details");
        setSupportActionBar(toolbar);

        itemList = Parcels.unwrap(getIntent().getParcelableExtra("itemList"));

        recyclerHospital = (RecyclerView) findViewById(R.id.recyclerHospital);
        recyclerHospital.setLayoutManager(new LinearLayoutManager(this));

        HospitalListRecycler hospitalListRecycler = new HospitalListRecycler(itemList,this);
        recyclerHospital.setAdapter(hospitalListRecycler);

        mAdView = (AdView) findViewById(R.id.adView2);

//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        AdUtil.loadAds(mAdView);
    }

}
