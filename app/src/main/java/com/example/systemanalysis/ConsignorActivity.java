package com.example.systemanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
        if (name.equals("") || email.equals("") || phone.equals("") ){
            AlertDialog alertDialog = new AlertDialog.Builder(ConsignorActivity.this).create();
            alertDialog.setTitle("เกิดข้อผิดพลาด");
            alertDialog.setMessage("กรุณาใส่ข้อมูลให้ครบถ้วนและถูกต้อง");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            return;
                        }
                    });
            alertDialog.show();

        }

        ConsignmentItemDao conDao = new ConsignmentItemDao();
        conDao.setName(name);
        conDao.setEmail(email);
        conDao.setPhoneNumber(phone);
        Call<ConsignmentItemDao> call = HttpManager.getInstance().getService().addConsignor(conDao);
        call.enqueue(new Callback<ConsignmentItemDao>() {
            @Override
            public void onResponse(Call<ConsignmentItemDao> call, Response<ConsignmentItemDao> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext() , "เพิ่มข้อมูลสำเร็จ" , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext() , HomeActivity.class);
                    startActivity(intent);


                }
                else {


                }
            }

            @Override
            public void onFailure(Call<ConsignmentItemDao> call, Throwable t) {

            }
        });

    }


}