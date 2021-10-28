package com.example.systemanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.systemanalysis.dao.AgreementItemDao;
import com.example.systemanalysis.dao.ConsignmentItemDao;
import com.example.systemanalysis.manager.Contextor;
import com.example.systemanalysis.manager.HttpManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsignorActivity extends AppCompatActivity {

    Button btnConfirm;
    EditText edtName;
    EditText edtEmail;
    EditText edtPhone;

    String name;
    String email;
    String phone;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consignor);
        initInstance();
    }

    private void initInstance(){
        btnConfirm = findViewById(R.id.btnConfirm);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);


        btnConfirm.setOnClickListener(v -> {
            addData();
        });

    }




    private void addData(){
        addConsignor();


    }

    private void addConsignor(){
        name = edtName.getText().toString();
        email = edtEmail.getText().toString();
        phone = edtPhone.getText().toString();

        ConsignmentItemDao conDao = new ConsignmentItemDao();
        conDao.setName(name);
        conDao.setEmail(email);
        conDao.setPhoneNumber(phone);
        Call<ConsignmentItemDao> call = HttpManager.getInstance().getService().addConsignor(conDao);
        call.enqueue(new Callback<ConsignmentItemDao>() {
            @Override
            public void onResponse(Call<ConsignmentItemDao> call, Response<ConsignmentItemDao> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext() , response.body().getName() , Toast.LENGTH_SHORT);


                }
                else {


                }
            }

            @Override
            public void onFailure(Call<ConsignmentItemDao> call, Throwable t) {

            }
        });

    }

    private void addAgreement(){
        AgreementItemDao agreement = new AgreementItemDao();
//        agreement.setPath(filePath);
        Call<AgreementItemDao> call = HttpManager.getInstance().getService().addAgreement(agreement);
        call.enqueue(new Callback<AgreementItemDao>() {
            @Override
            public void onResponse(Call<AgreementItemDao> call,
                                   Response<AgreementItemDao> response) {
                if (response.isSuccessful()){

                }else {

                }

            }

            @Override
            public void onFailure(Call<AgreementItemDao> call, Throwable t) {

            }
        });

    }

    private void addLand(){


    }

}