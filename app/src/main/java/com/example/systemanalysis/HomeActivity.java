package com.example.systemanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    Button btnLandList;
    Button btnForSell;
    Button btnAddLand;
    Button btnAddCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initInstance();
    }

    private void initInstance(){
        btnLandList = findViewById(R.id.btnLandList);
        btnLandList.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext() , LandListActivity.class);
            startActivity(intent);
        });
        btnForSell = findViewById(R.id.btnForSell);
        btnForSell.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext() , ConsignorActivity.class);
            startActivity(intent);
        });

        btnAddLand = findViewById(R.id.btnAddLand);
        btnAddLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , ConsignorListActivity.class);
                startActivity(intent);
            }
        });
        btnAddCustomer = findViewById(R.id.btnAddCustomer);
        btnAddCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext() , CustomerListActivity.class);
                startActivity(intent);
            }
        });


    }
}