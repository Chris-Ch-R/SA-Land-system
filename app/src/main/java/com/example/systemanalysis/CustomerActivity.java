package com.example.systemanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.systemanalysis.dao.CustomerItemDao;
import com.example.systemanalysis.manager.HttpManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerActivity extends AppCompatActivity {
    Button btnConfirm;
    EditText edtName;
    EditText edtEmail;
    EditText edtPhone;

    String customerName;
    String email;
    String phone;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        Intent intent = getIntent();
        landID = intent.getIntExtra("land_id" , -1);
        latitude = intent.getDoubleExtra("latitude" , 13.736717);
        longitude = intent.getDoubleExtra("longitude" , 13.736717);
        name = intent.getStringExtra("name");
        deedNumber = intent.getStringExtra("deedNumber" );
        landNumber = intent.getStringExtra("landNumber" );
        landAddress = intent.getStringExtra("landAddress" );
        tombon = intent.getStringExtra("Tombon");
        amphure = intent.getStringExtra("amphure");
        province = intent.getStringExtra("province" );
        landArea = intent.getIntExtra("landArea" , 100);

        initInstance();
    }

    private void initInstance(){
        btnConfirm = findViewById(R.id.btnConfirm);
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);

        btnConfirm.setOnClickListener(v -> {
            addCustomer();
        });
    }

    private void addCustomer(){
        customerName = edtName.getText().toString();
        email = edtEmail.getText().toString();
        phone = edtPhone.getText().toString();
        if (name.equals("") || email.equals("") || phone.equals("") ){
            AlertDialog alertDialog = new AlertDialog.Builder(CustomerActivity.this).create();
            alertDialog.setTitle("เกิดข้อผิดพลาด");
            alertDialog.setMessage("กรุณาใส่ข้อมูลให้ครบถ้วนและถูกต้อง");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    (dialog, which) -> {
                        dialog.dismiss();
                        return;
                    });
            alertDialog.show();

        }

        CustomerItemDao customer = new CustomerItemDao();
        customer.setName(customerName);
        customer.setEmail(email);
        customer.setPhoneNumber(phone);
        customer.setLandId(landID);
        Call<CustomerItemDao> call = HttpManager.getInstance().getService().addCustomer(customer);
        call.enqueue(new Callback<CustomerItemDao>() {
            @Override
            public void onResponse(Call<CustomerItemDao> call,
                                   Response<CustomerItemDao> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext() , "บันทึกสำเร็จ" , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                    intent.putExtra("landID" , landID);
                    intent.putExtra("latitude" ,latitude);
                    intent.putExtra("longitude" , longitude);
                    intent.putExtra("name" , name);
                    intent.putExtra("deedNumber" , deedNumber);
                    intent.putExtra("landNumber" , landNumber);
                    intent.putExtra("landAddress" , landAddress);
                    intent.putExtra("Tombon" , tombon);
                    intent.putExtra("amphure" , amphure);
                    intent.putExtra("province" , province);
                    intent.putExtra("landArea" , landArea);
                    startActivity(intent);
                }else {
//                    Toast.makeText(getApplicationContext() , response + "" , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<CustomerItemDao> call, Throwable t) {
                Toast.makeText(getApplicationContext() , "Can't connect to database" , Toast.LENGTH_SHORT).show();


            }
        });





    }
}