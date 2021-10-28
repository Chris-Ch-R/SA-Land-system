package com.example.systemanalysis;

import androidx.appcompat.app.AppCompatActivity;

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

    String name;
    String email;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
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
        name = edtName.getText().toString();
        email = edtEmail.getText().toString();
        phone = edtPhone.getText().toString();

        CustomerItemDao customer = new CustomerItemDao();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhoneNumber(phone);
        Call<CustomerItemDao> call = HttpManager.getInstance().getService().addCustomer(customer);
        call.enqueue(new Callback<CustomerItemDao>() {
            @Override
            public void onResponse(Call<CustomerItemDao> call,
                                   Response<CustomerItemDao> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplicationContext() , "Add Success" , Toast.LENGTH_SHORT).show();
                }else {

                }

            }

            @Override
            public void onFailure(Call<CustomerItemDao> call, Throwable t) {
                Toast.makeText(getApplicationContext() , "Can't connect to database" , Toast.LENGTH_SHORT).show();


            }
        });





    }
}