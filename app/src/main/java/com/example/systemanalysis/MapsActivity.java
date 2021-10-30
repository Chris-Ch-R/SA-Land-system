package com.example.systemanalysis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    int landID;
    double latitude , longitude;
    String name;
    String deedNumber;
    String landNumber;
    String landAddress;
    String tombon;
    String amphure;
    String province;
    int landArea;
    Button btnCustomer;
    Button btnAds;
    TextView tvName;
    TextView tvDeedNumber;
    TextView tvLandNumber;
    TextView tvTombon;
    TextView tvAmphure;
    TextView tvProvince;
    TextView tvLandArea;

//    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        landID = intent.getIntExtra("landID" , -1) ;
        latitude = intent.getDoubleExtra("latitude" , 13.736717);
        name = intent.getStringExtra("name");
        deedNumber = intent.getStringExtra("deedNumber" );
        landNumber = intent.getStringExtra("landNumber" );
        landAddress = intent.getStringExtra("landAddress" );
        tombon = intent.getStringExtra("Tombon");
        amphure = intent.getStringExtra("amphure");
        province = intent.getStringExtra("province" );
        landArea = intent.getIntExtra("landArea" , 100);


//        binding = ActivityMapsBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initInstance();
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

        // Add a marker in Sydney and move the camera

        LatLng landLocation = new LatLng(latitude , longitude);

        mMap.addMarker(new MarkerOptions().position(landLocation)
                .title(landAddress  )
                .snippet(tombon + " " + amphure + " " + province));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(landLocation , 5));
//        mMap.animateCamera(CameraUpdateFactory.zoomIn());
    }

    private void initInstance(){
        btnCustomer = findViewById(R.id.btnCustomer);
        btnAds = findViewById(R.id.btnAds);
        tvName = findViewById(R.id.tvName);
        tvDeedNumber = findViewById(R.id.tvDeedNumber);
        tvLandNumber = findViewById(R.id.tvLandNumber);
        tvTombon = findViewById(R.id.tvTombon);
        tvAmphure = findViewById(R.id.tvAmphure);
        tvProvince = findViewById(R.id.tvProvince);
        tvLandArea = findViewById(R.id.tvLandArea);

        setText();

        btnCustomer.setOnClickListener(v -> {
            Intent intentCustomer = new Intent(getApplicationContext() , InterestedListActivity.class );
            intentCustomer.putExtra("landID" , landID);
            startActivity(intentCustomer);
        });
        btnAds.setOnClickListener(v -> {
            Intent intentAds = new Intent(getApplicationContext() , AdsMapsActivity.class );
            intentAds.putExtra("landID" , landID);
            startActivity(intentAds);

        });
    }

    private void setText(){
        tvName.setText(name);
        tvDeedNumber.setText(deedNumber);
        tvLandNumber.setText(landNumber);
        tvTombon.setText(tombon);
        tvAmphure.setText(amphure);
        tvProvince.setText(province);
        tvLandArea.setText(landArea + " ตารางวา");
    }
}