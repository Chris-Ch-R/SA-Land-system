package com.example.systemanalysis;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.systemanalysis.dao.AdsItemDao;
import com.example.systemanalysis.manager.HttpManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.systemanalysis.databinding.ActivityAdsMapsBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdsMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityAdsMapsBinding binding;
    int landID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        landID = intent.getIntExtra("landID" , -1);

        binding = ActivityAdsMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Call<List<AdsItemDao>> call = HttpManager.getInstance().getService().loadAdsList2();
        call.enqueue(new Callback<List<AdsItemDao>>() {
            @Override
            public void onResponse(Call<List<AdsItemDao>> call,
                                   Response<List<AdsItemDao>> response) {
                if (response.isSuccessful()){
                    List<AdsItemDao> dao = response.body();
                    for (int i = 0 ; i < dao.size() ; i++){
                        LatLng latLng = new LatLng(dao.get(i).getLatitude() , dao.get(i).getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title(dao.get(i).getMessage()));
                    }

                }else {

                }
            }

            @Override
            public void onFailure(Call<List<AdsItemDao>> call, Throwable t) {

            }
        });

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//        getDevi
    }

    private void initInstance(){

    }
}