package com.mayank7319gmail.hospitallocator.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.mayank7319gmail.hospitallocator.R;
import com.mayank7319gmail.hospitallocator.models.PlaceList;
import com.mayank7319gmail.hospitallocator.models.SinglePlace;
import com.mayank7319gmail.hospitallocator.recycler.HospitalListRecycler;
import com.mayank7319gmail.hospitallocator.rest_api.GooglePlacesApi;
import com.mayank7319gmail.hospitallocator.rest_api.HospitalListClient;
import com.mayank7319gmail.hospitallocator.utils.AdUtil;
import com.wang.avi.AVLoadingIndicatorView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mayank7319gmail.hospitallocator.utils.LoadingUtil.enableDisableView;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerHospital;
    ArrayList<SinglePlace> itemList;
    AdView mAdView;
    FrameLayout fader, listFrame;
    AVLoadingIndicatorView avi;
    TextView tvDisplayResult;

    GooglePlacesApi googlePlacesApi;
    HospitalListClient hospitalListClient;

    public static final String TAG = "list";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Details");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerHospital = (RecyclerView) findViewById(R.id.recyclerHospital);
        recyclerHospital.setLayoutManager(new LinearLayoutManager(this));

        fader = (FrameLayout) findViewById(R.id.fader);
        listFrame = (FrameLayout) findViewById(R.id.content_main);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        tvDisplayResult = findViewById(R.id.tvDisplayResult);

        mAdView = (AdView) findViewById(R.id.adView2);

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        AdUtil.loadAds(mAdView);

        stopLoadingAnimation();
        tvDisplayResult.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
//            Log.d(TAG, "onCreate: search started");

            setLoadingAnimation();
            String query = intent.getStringExtra(SearchManager.QUERY);

            toolbar.setTitle("Search results for '"+query+"'");

            googlePlacesApi = new GooglePlacesApi(getApplicationContext());
            hospitalListClient = googlePlacesApi.getHospitalListClient();

            HashMap<String, String > params = googlePlacesApi.getQueryParams(MainActivity.curLocation, GooglePlacesApi.TYPE_HOSPITAL, GooglePlacesApi.RANKBY_PROMINENCE);
            params.put("radius","50000");
            params.put("name", query);

            hospitalListClient.getNearbyHospitals(params).enqueue(new Callback<PlaceList>() {
                @Override
                public void onResponse(Call<PlaceList> call, Response<PlaceList> response) {
//                    Log.d(TAG, "onResponse: resp received");
                    PlaceList placeList = response.body();

                    if(placeList != null){
                        stopLoadingAnimation();
                        itemList = placeList.places;
                        if(itemList.size() == 0)
                            tvDisplayResult.setVisibility(View.VISIBLE);
                        else
                            bindRecyclerView();

                    }

                }

                @Override
                public void onFailure(Call<PlaceList> call, Throwable t) {
//                    Log.d(TAG, "onFailure: cannot access places api");
                    Toast.makeText(getApplicationContext(),"Unable to access server. Please try again later",Toast.LENGTH_SHORT).show();
                    tvDisplayResult.setVisibility(View.VISIBLE);
                }
            });
        }
        else {
            itemList = Parcels.unwrap(intent.getParcelableExtra("itemList"));
            bindRecyclerView();
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    void bindRecyclerView(){
        HospitalListRecycler hospitalListRecycler = new HospitalListRecycler(itemList,this);
        recyclerHospital.setAdapter(hospitalListRecycler);
    }

    void setLoadingAnimation(){
        enableDisableView(listFrame, false);
        tvDisplayResult.setVisibility(View.INVISIBLE);
        fader.setVisibility(View.VISIBLE);
        avi.show();
    }

    void stopLoadingAnimation(){
        enableDisableView(listFrame, true);
        fader.setVisibility(View.GONE);
        avi.hide();
    }

}
