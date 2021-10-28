package com.example.systemanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.systemanalysis.dao.AmphureDao;
import com.example.systemanalysis.dao.ConsignmentItemDao;
import com.example.systemanalysis.dao.ProvinceDao;
import com.example.systemanalysis.dao.TombonDao;
import com.example.systemanalysis.manager.HttpManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsignorNextActivity extends AppCompatActivity {
    Button uploadFile;
    Button btnSaveLand;
    TextView tvAgreement;
    Spinner spProvince;
    Spinner spTombon;
    Spinner spAmphure;
    EditText edtLandNumber;
    EditText edtDeedNumber;
    EditText edtLandAddress;
    EditText edtLandAreaReal;
    EditText edtLandAreaDeed;
    EditText edtPricePerUnit;

    String filePath;

    String deedNumber;
    String landNumber;
    String address;
    String landAreaReal;
    String landAreaDeed;
    String amphurId;
    String tombonId;

    String consignor;

    int realArea;
    int deedArea;
    int pricePerUnit;

    List<ConsignmentItemDao> consignmentItemDaoList ;
    List<ProvinceDao> province ;
    List<AmphureDao> amphure ;
    List<TombonDao> tombon ;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consignor_next);

        initInstance();
    }

    private void initInstance() {
        tvAgreement = findViewById(R.id.tvAgreement);

        uploadFile = findViewById(R.id.uploadFile);
        uploadFile.setOnClickListener(v -> {
            showChooser();
        });

        spProvince = findViewById(R.id.spProvince);
        spAmphure = findViewById(R.id.spAmphure);
        spTombon = findViewById(R.id.spTombon);

        edtLandNumber = findViewById(R.id.edtLandNumber);
        edtDeedNumber = findViewById(R.id.edtDeedNumber);
        edtLandAddress = findViewById(R.id.edtLandAddress);
        edtLandAreaReal = findViewById(R.id.edtLandAreaReal);
        edtLandAreaDeed = findViewById(R.id.edtLandAreaDeed);
        edtPricePerUnit = findViewById(R.id.edtPricePerUnit);

        btnSaveLand = findViewById(R.id.btnSaveLand);

        fetchProvince();



        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<AmphureDao> provinceAmphure = province.get(position).getAmphure();
//                tvAgreement.setText(provinceAmphure + "");


                amphure = new ArrayList<>(provinceAmphure);
                ArrayAdapter<AmphureDao> amphureAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, amphure);
                spAmphure.setAdapter(amphureAdapter);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//        spAmphure.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                List<TombonDao> amphuresTombon = amphure.get(position).getTombon();
//
//                tombon = new ArrayList<>(amphuresTombon);
//                ArrayAdapter<TombonDao> tombonAdapter = new ArrayAdapter<>(getApplication() , android.R.layout.simple_spinner_item , tombon);
//                spTombon.setAdapter(tombonAdapter);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        spTombon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

//        ConsignmentItemDao  consignmentItemDao = (ConsignmentItemDao) spConsignor.getSelectedItem();






    }


    int requestCode = 1;

    private void showChooser() {
//        Intent target = FileUtils.createGetContentIntent();
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, requestCode);


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCode && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }

            Uri uri = data.getData();
//            Toast.makeText(getApplicationContext() , uri.getPath() , Toast.LENGTH_LONG).show();
            tvAgreement.setText(uri.getPath());
            filePath = uri.getPath();

        }
    }

    private void fetchProvince() {
        Call<List<ProvinceDao>> call = HttpManager.getInstance().getService().loadProvince2();
        call.enqueue(new Callback<List<ProvinceDao>>() {
            @Override
            public void onResponse(Call<List<ProvinceDao>> call, Response<List<ProvinceDao>> response) {
                if (response.isSuccessful()){
                    List<ProvinceDao> dao = response.body();
                    province = new ArrayList<>(dao);


                    ArrayAdapter<ProvinceDao> provinceAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, dao);
                    spProvince.setAdapter(provinceAdapter);
                    Toast.makeText(getApplicationContext() , "success" , Toast.LENGTH_SHORT).show();



                }else {
                    Toast.makeText(getApplicationContext() , "Unsuccess" , Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<List<ProvinceDao>> call, Throwable t) {
                Toast.makeText(getApplicationContext() , t.toString() , Toast.LENGTH_SHORT).show();
                tvAgreement.setText(t.toString());
            }
        });

    }

    private void saveLandData(){

    }

}