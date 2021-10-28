package com.example.systemanalysis.manager;

import android.content.Context;

import com.example.systemanalysis.dao.LandItemDao;
import com.example.systemanalysis.manager.http.ApiService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {

    private static HttpManager instance;

    public static HttpManager getInstance(){
        if (instance == null){
            instance = new HttpManager();
        }
        return instance;
    }

    private Context mContext;
    private ApiService service;

    private HttpManager(){
        mContext = Contextor.getInstance().getContext();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.43.138:8000/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(ApiService.class);
    }

    public ApiService getService(){
        return service;
    }


}
