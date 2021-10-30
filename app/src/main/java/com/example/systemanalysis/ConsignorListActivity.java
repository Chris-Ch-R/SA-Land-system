package com.example.systemanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.systemanalysis.adapter.ConsignmentListAdapter;
import com.example.systemanalysis.adapter.CustomerListAdapter;
import com.example.systemanalysis.dao.ConsignmentItemDao;
import com.example.systemanalysis.dao.CustomerItemDao;
import com.example.systemanalysis.manager.ConsignmentListManager;
import com.example.systemanalysis.manager.CustomerListManager;
import com.example.systemanalysis.manager.HttpManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConsignorListActivity extends AppCompatActivity {
    ListView lvConsignor;
    ConsignmentListAdapter consignorAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consignor_list);
        initInstance();
    }

    private void initInstance(){
        lvConsignor = findViewById(R.id.lvConsignor);
        consignorAdapter = new ConsignmentListAdapter();
        lvConsignor.setAdapter(consignorAdapter);

        Call<List<ConsignmentItemDao>> call = HttpManager.getInstance().getService().loadConsignmentList();
        call.enqueue(new Callback<List<ConsignmentItemDao>>() {
            @Override
            public void onResponse(Call<List<ConsignmentItemDao>> call,
                                   Response<List<ConsignmentItemDao>> response) {
                if (response.isSuccessful()){
                    List<ConsignmentItemDao> dao = response.body();
                    ConsignmentListManager.getInstance().setDao(dao);
                    consignorAdapter.notifyDataSetChanged();
                    lvConsignor.setOnItemClickListener((parent, view, position, id) -> {
                        Intent intentAddlandNext = new Intent(getApplicationContext() , ConsignorNextActivity.class);
                        intentAddlandNext.putExtra("consignment_id" +
                                "" , dao.get(position).getId());
                        startActivity(intentAddlandNext);

                    });
                    Log.i("response", "onResponse: success " + dao.size());

                }else {
                    Log.i("response", "onResponse: Connect succes but no data");

                }

            }

            @Override
            public void onFailure(Call<List<ConsignmentItemDao>> call,
                                  Throwable t) {


            }
        });
    }
}