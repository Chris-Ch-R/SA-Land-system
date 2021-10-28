package com.example.systemanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.systemanalysis.dao.CustomerItemDao;
import com.example.systemanalysis.manager.HttpManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerDetailActivity extends AppCompatActivity {

    int customerID;
    TextView tvCustomerName;
    TextView tvCustomerEmail;
    TextView tvCustomerPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        Intent intent = getIntent();
        customerID = intent.getIntExtra("customerID" , -1);
        initInstance();
    }

    private void initInstance(){
        tvCustomerName = findViewById(R.id.tvCustomerName);
        tvCustomerEmail = findViewById(R.id.tvCustomerEmail);
        tvCustomerPhone = findViewById(R.id.tvCustomerPhone);

        Call<CustomerItemDao> call = HttpManager.getInstance().getService().loadCustomerDetail(customerID);
        call.enqueue(new Callback<CustomerItemDao>() {
            @Override
            public void onResponse(Call<CustomerItemDao> call,
                                   Response<CustomerItemDao> response) {
                if (response.isSuccessful()){
                    CustomerItemDao dao = response.body();
                    tvCustomerName.setText(dao.getName());
                    tvCustomerEmail.setText(dao.getEmail());
                    tvCustomerPhone.setText(dao.getPhoneNumber());

                }else {

                }

            }

            @Override
            public void onFailure(Call<CustomerItemDao> call,
                                  Throwable t) {


            }
        });

    }
}