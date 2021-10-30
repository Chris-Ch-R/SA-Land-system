package com.example.systemanalysis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.systemanalysis.adapter.LandListAdapter;
import com.example.systemanalysis.dao.LandItemCollection;
import com.example.systemanalysis.dao.LandItemDao;
import com.example.systemanalysis.manager.HttpManager;
import com.example.systemanalysis.manager.LandListManager;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LandListActivity extends AppCompatActivity {
    ListView listView;
    LandListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initInstance();
    }

    private void initInstance(){
        listView = findViewById(R.id.listview);
        listAdapter = new LandListAdapter();
        listView.setAdapter(listAdapter);

        Call<List<LandItemDao>> call = HttpManager.getInstance().getService().loadLandList();
        call.enqueue(new Callback<List<LandItemDao>>() {
            @Override
            public void onResponse(Call<List<LandItemDao>> call,
                                   Response<List<LandItemDao>> response) {
                if (response.isSuccessful()){
                    List<LandItemDao> dao = response.body();
                    LandListManager.getInstance().setDao(dao);
                    listAdapter.notifyDataSetChanged();

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getApplicationContext() , MapsActivity.class);
                            Log.i("landid", "onItemClick: " +  dao.get(position).getId());
                            Bundle bundle = new Bundle();
                            intent.putExtra("landID" , dao.get(position).getId());
                            intent.putExtra("latitude" , dao.get(position).getLatitude());
                            intent.putExtra("name" , dao.get(position).getOwner().getName());
                            intent.putExtra("deedNumber" , dao.get(position).getDeedNumber());
                            intent.putExtra("landNumber" , dao.get(position).getLandNumber());
                            intent.putExtra("landAddress" , dao.get(position).getLandAddress());
                            intent.putExtra("Tombon" , dao.get(position).getTombon().getNameTh());
                            intent.putExtra("amphure" , dao.get(position).getTombon().getAmphure().getNameTh());
                            intent.putExtra("province" , dao.get(position).getTombon().getAmphure().getProvince().getNameTh());
                            intent.putExtra("landArea" , dao.get(position).getLandAreaReal());
                            startActivity(intent);
                        }
                    });
//                    Log.i("response", "onResponse: Success:" + dao.getData().get(0).getAgreement());

                }else {
                    try {
                        Log.i("elseresponse", "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext() , "error", Toast.LENGTH_LONG);
                }

            }

            @Override
            public void onFailure(Call<List<LandItemDao>> call,
                                  Throwable t) {
                Log.i("response", "onFailure:" + t.toString());
                Toast.makeText(getApplicationContext() , "Fail", Toast.LENGTH_LONG);

            }
        });


    }
}