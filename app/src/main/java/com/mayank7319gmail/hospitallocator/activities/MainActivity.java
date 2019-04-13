package com.mayank7319gmail.hospitallocator.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mayank7319gmail.hospitallocator.R;
import com.mayank7319gmail.hospitallocator.models.DistanceDuration;
import com.mayank7319gmail.hospitallocator.models.DistanceResult;
import com.mayank7319gmail.hospitallocator.models.PlaceList;
import com.mayank7319gmail.hospitallocator.models.SinglePlace;
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

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Marker curmarker;
    static LatLng defLocation = new LatLng(28.5, 77); //Delhi
    static LatLng curLocation = defLocation;

    int locationType = GooglePlacesApi.TYPE_HOSPITAL;
    int locationRankby = GooglePlacesApi.RANKBY_PROMINENCE;

    public static final String  privacy_policy_url = "https://medloco-privacy-policy-git-master.mayankg.now.sh/";

    LocationManager locMan;
    LocationListener locLis;
    Context ctx = this;
    public static final String TAG = "HL";

    boolean mapReady = false;
    float mapAccuracy = 10000;

    FrameLayout fader;
    AVLoadingIndicatorView avi;

    GooglePlacesApi googlePlacesApi;
    HospitalListClient hospitalListClient;
    PlaceList placeList;
    DistanceResult distanceResult;

    FrameLayout mainFrame;
    Button btnFilter, btnDetails;
    AdView mAdView;

    Spinner spinnerType, spinnerRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        //toolbar.setTitle("Hospital Locator");
        setSupportActionBar(toolbar);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fader = (FrameLayout) findViewById(R.id.fader);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);

        btnFilter = (Button) findViewById(R.id.btnFilter);
        btnDetails = (Button) findViewById(R.id.btnDetails);
        mAdView = (AdView) findViewById(R.id.adView);
        mainFrame = (FrameLayout) findViewById(R.id.mainFrame);

        setLoadingAnimation();

        AdUtil.initAds(ctx);
        AdUtil.loadAds(mAdView);

//        MobileAds.initialize(ctx,"ca-app-pub-3940256099942544~3347511713");
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

            googlePlacesApi = new GooglePlacesApi(ctx);
            hospitalListClient = googlePlacesApi.getHospitalListClient();

        btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDetailList();
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionDialog();
            }
        });


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    123);
        } else {
            locMan = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean gpsEnabled, networkEnabled;

            gpsEnabled = locMan.isProviderEnabled(LocationManager.GPS_PROVIDER);
            networkEnabled = locMan.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!gpsEnabled && !networkEnabled) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
                dialog.setMessage(getResources().getString(R.string.gps_network_not_enabled));

                dialog.setPositiveButton(getResources().getString(R.string.open_location_settings),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(i);
                                Toast.makeText(ctx, "Restart app after enabling GPS", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });

                dialog.setNegativeButton(getResources().getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Nothing to do here
                                Toast.makeText(ctx, "Enable GPS to allow app to function", Toast.LENGTH_SHORT).show();
                            }
                        });

                dialog.show();
            } else {
                //Get current location coord
//                Location temp = locMan.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                locLis = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        curLocation = new LatLng(location.getLatitude(), location.getLongitude());
                        /*Log.d(TAG, "onLocationChanged: lat: "+curLocation.latitude);
                        Log.d(TAG, "onLocationChanged: long: "+curLocation.longitude);
                        Log.d(TAG, "onLocationChanged: accuracy: "+location.getAccuracy());*/
                        mapAccuracy = location.getAccuracy();
                        if(mMap != null)
                        initMapPointer(curLocation);
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                };

//                Log.d(TAG, "onCreate: Trying for location");
                locMan.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        100,
                        1,
                        locLis
                );

                locMan.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        100,
                        1,
                        locLis
                );
            }
        }

    }

    @Override
    protected void onStart() {
        if(mAdView != null)
            AdUtil.loadAds(mAdView);
        super.onStart();
    }

    void setLoadingAnimation(){
       enableDisableView(mainFrame, false);
       fader.setVisibility(View.VISIBLE);
       avi.show();
   }

    void stopLoadingAnimation(){
        enableDisableView(mainFrame, true);
        fader.setVisibility(View.GONE);
        avi.hide();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(mMap == null)
            Log.d(TAG, "onMapReady: map is null");

//        Log.d(TAG, "onMapReady: Map is ready");

        //locMan.removeUpdates(locLis);
        initMapPointer(curLocation);
    }

    void initMapPointer(LatLng loc){

        initCurrentPointer(loc);
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc,13));

        if(mapAccuracy!= 10000) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locMan.removeUpdates(locLis);
            getHospitalLocation(curLocation);
//            Log.d(TAG, "initMapPointer: Map location is correct");
        }
    }

    void initCurrentPointer(LatLng loc){
        mMap.clear();

        curmarker = mMap.addMarker(new MarkerOptions().position(loc).title("Current Location")
                .snippet("("+loc.latitude+","+loc.longitude+")")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    }

    void addMapMarker(LatLng loc, String name, String vicinity){
        mMap.addMarker(new MarkerOptions().position(loc).title(name)
                .snippet(vicinity)
                );

//        Log.d(TAG, "addMapMarker: marker "+loc);
    }

    void getHospitalLocation(LatLng loc){
        /*HashMap<String,String> params = new HashMap<>();
        params.put("key", GooglePlacesApi.WEB_KEY);
        String latlng = loc.latitude+","+loc.longitude;
        params.put("location",latlng);
        params.put("type","hospital");
        params.put("radius",String.valueOf(GooglePlacesApi.SEARCH_RADIUS));
*/
        HashMap<String,String> params = googlePlacesApi.getQueryParams(loc, locationType, locationRankby);

        hospitalListClient.getNearbyHospitals(params).enqueue(new Callback<PlaceList>() {
            @Override
            public void onResponse(Call<PlaceList> call, Response<PlaceList> response) {
//                Log.d(TAG, "onResponse: Response recieved");
                placeList = response.body();

                if(placeList != null && placeList.places != null && !placeList.places.isEmpty()){
                    int s = placeList.places.size();
                    int len = s>10 ? 10 : s;    //Limiting to a maximum of 10 right now
                    for(int i = 0; i<len; i++){
                        SinglePlace place = placeList.places.get(i);
                        addMapMarker(place.getLoc(),place.getName(),place.getVicinity());
                    }

                    getDistance();
                }
                else {
                    // Display message for lack of results.
                    Toast.makeText(ctx, "No results found in a 5km radius.",Toast.LENGTH_SHORT).show();
                }
                //addMapMarker(new LatLng(28.6566,77.18432),"Test loc","testing");
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curLocation,14));

                mapReady = true;
                curmarker.showInfoWindow();
                stopLoadingAnimation();
//                Log.d(TAG, "onResponse: Fininshed adding location pointers");
            }

            @Override
            public void onFailure(Call<PlaceList> call, Throwable t) {
//                Log.d(TAG, "onFailure: cannot access places api");
                Toast.makeText(ctx,"Unable to access server. Please try again later",Toast.LENGTH_SHORT).show();
            }
        });
    }

    void showDetailList(){
        Intent i = new Intent(this, ListActivity.class);
        i.putExtra("itemList", Parcels.wrap(placeList.places));
        startActivity(i);
    }

    void getDistance(){
        if(placeList.places.isEmpty())
            return;

        String destination = "";

        for(int i=0; i<placeList.places.size()-1; i++){
            SinglePlace place = placeList.places.get(i);
            destination += place.getLoc().latitude+ "," +place.getLoc().longitude+ "|";
        }

        SinglePlace place = placeList.places.get(placeList.places.size()-1);
        destination += place.getLoc().latitude+ "," +place.getLoc().longitude;


        HashMap<String,String> params = new HashMap<>();
        params.put("key", GooglePlacesApi.WEB_KEY);
        params.put("origins",curLocation.latitude+","+curLocation.longitude);
        params.put("destinations",destination);

        hospitalListClient.getHospitalDistances(params).enqueue(new Callback<DistanceResult>() {
            @Override
            public void onResponse(Call<DistanceResult> call, Response<DistanceResult> response) {
                distanceResult = response.body();

                if(distanceResult != null){
                    ArrayList<DistanceDuration> distanceDurations = distanceResult.getRows().get(0).getElements();
                    if(distanceDurations == null)
                        return;

                    for(int i=0; i<distanceDurations.size(); i++){
                        DistanceDuration d = distanceDurations.get(i);

//                        Log.d(TAG, "onResponse: distance"+d.getDistance().getText());
//                        Log.d(TAG, "onResponse: duration"+d.getDuration().getText());

                        placeList.places.get(i).setDistance(d.getDistance().getValue());
                        placeList.places.get(i).setDistanceString(d.getDistance().getText());
                        placeList.places.get(i).setTimeMinutes(d.getDuration().getValue());
                        placeList.places.get(i).setTimeString(d.getDuration().getText());
                    }
                }
                else{
                    Toast.makeText(ctx, "Unable to fetch data from the server. Please try again later",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DistanceResult> call, Throwable t) {
                Toast.makeText(ctx,"Unable to access server. Please try again later",Toast.LENGTH_SHORT).show();
//                Log.d(TAG, "onFailure: cannot fetch distances");
            }
        });
    }

    void showOptionDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View optionView = inflater.inflate(R.layout.option_dialog_layout,null);

        spinnerType = (Spinner) optionView.findViewById(R.id.spinnerType);
        spinnerRank = (Spinner) optionView.findViewById(R.id.spinnerRank);

        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.array_option_type));
        ArrayAdapter<String> adapterRank = new ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.array_option_rank));

        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterRank.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapterType);
        spinnerRank.setAdapter(adapterRank);

        builder.setTitle("Filter Search");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String typeString = spinnerType.getSelectedItem().toString();
                String rankString = spinnerRank.getSelectedItem().toString();

                if(googlePlacesApi.getType(typeString) == locationType && googlePlacesApi.getRank(rankString) == locationRankby){
                    Toast.makeText(ctx,"The selected filter has already been applied",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
                else {
                    locationType = googlePlacesApi.getType(typeString);
                    locationRankby = googlePlacesApi.getRank(rankString);

                    mapReady = false;
                    setLoadingAnimation();
                    initCurrentPointer(curLocation);
                    getHospitalLocation(curLocation);
                }
            }
        });

        builder.setView(optionView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 123){
            Toast.makeText(this,"Restart app to use permissions",Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.privacy:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(privacy_policy_url));
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
