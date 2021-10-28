package com.example.systemanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.systemanalysis.adapter.CustomerListAdapter;

public class ConsignorListActivity extends AppCompatActivity {
    ListView lvConsignor;
    CustomerListAdapter consignorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consignor_list);
        initInstance();
    }

    private void initInstance(){
        lvConsignor = findViewById(R.id.lvConsignor);
        consignorAdapter = new CustomerListAdapter();
        lvConsignor.setAdapter(consignorAdapter);
    }
}