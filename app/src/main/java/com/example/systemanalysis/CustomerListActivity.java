package com.example.systemanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.systemanalysis.adapter.CustomerListAdapter;
import com.example.systemanalysis.dao.CustomerItemDao;
import com.example.systemanalysis.manager.CustomerListManager;
import com.example.systemanalysis.manager.HttpManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerListActivity extends AppCompatActivity {

    ListView lwCustomer;
    CustomerListAdapter customerListAdapter;
    int landID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);
        Intent intent = getIntent();
        landID = intent.getIntExtra("landID" , -1);
        initInstance();
    }

    private void initInstance(){
        lwCustomer = findViewById(R.id.lwCustomer);
        customerListAdapter = new CustomerListAdapter();
        lwCustomer.setAdapter(customerListAdapter);

        Call<List<CustomerItemDao>> call = HttpManager.getInstance().getService().loadCustomerList();
        call.enqueue(new Callback<List<CustomerItemDao>>() {
            @Override
            public void onResponse(Call<List<CustomerItemDao>> call,
                                   Response<List<CustomerItemDao>> response) {
                if (response.isSuccessful()){
                    List<CustomerItemDao> dao = response.body();
                    CustomerListManager.getInstance().setDao(dao);
                    customerListAdapter.notifyDataSetChanged();
                    lwCustomer.setOnItemClickListener((parent, view, position, id) -> {
//                        Intent intentCustomerDetail = new Intent(getApplicationContext() , CustomerDetailActivity.class);
//                        intentCustomerDetail.putExtra("customerID" , dao.get(position).getId());
//                        startActivity(intentCustomerDetail);

                    });
                    Log.i("response", "onResponse: success :" +dao.get(0).getName());

                }else {
                    Log.i("response", "onResponse: Connect succes but no data");

                }

            }

            @Override
            public void onFailure(Call<List<CustomerItemDao>> call,
                                  Throwable t) {


            }
        });

    }
}