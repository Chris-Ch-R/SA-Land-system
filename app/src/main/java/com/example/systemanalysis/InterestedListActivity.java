package com.example.systemanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.systemanalysis.adapter.CustomerListAdapter;
import com.example.systemanalysis.dao.LandItemDao;
import com.example.systemanalysis.manager.CustomerListManager;
import com.example.systemanalysis.manager.HttpManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InterestedListActivity extends AppCompatActivity {
    ListView lvInterestCustomer;
    CustomerListAdapter customerListAdapter;
    int landID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interested_list);
        Intent intent = getIntent();
        landID = intent.getIntExtra("landID" , -1);
        initInstance();
    }

    private void initInstance(){
        lvInterestCustomer = findViewById(R.id.lvInterestCustomer);


        customerListAdapter = new CustomerListAdapter();

        lvInterestCustomer.setAdapter(customerListAdapter);

        Call<LandItemDao> call = HttpManager.getInstance().getService().loadInterestedList(landID);
        call.enqueue(new Callback<LandItemDao>() {
            @Override
            public void onResponse(Call<LandItemDao> call,
                                   Response<LandItemDao> response) {
                if (response.isSuccessful()){
                    LandItemDao dao = response.body();
                    CustomerListManager.getInstance().setDao(dao.getCustomers());
                    customerListAdapter.notifyDataSetChanged();
                    lvInterestCustomer.setOnItemClickListener((parent, view, position, id) -> {
                        Intent intentCustomerDetail = new Intent(getApplicationContext() , CustomerDetailActivity.class);
                        intentCustomerDetail.putExtra("customerID" , dao.getCustomers().get(position).getId());
                        intentCustomerDetail.putExtra("landID" , landID);
                        startActivity(intentCustomerDetail);

                    });

                }else {
                    Toast.makeText(getApplicationContext() , "เกิดข้อผิดพลาดในการดึงข้อมูล" , Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LandItemDao> call,
                                  Throwable t) {
                Toast.makeText(getApplicationContext() , "ไม่สามารถเชื่อมต่อกับเซิฟเวอร์ได้" , Toast.LENGTH_SHORT).show();

            }
        });


    }
}