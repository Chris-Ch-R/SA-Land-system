package com.example.systemanalysis.manager;

import android.content.Context;

import com.example.systemanalysis.dao.CustomerItemDao;

import java.util.List;

public class CustomerListManager {

    private static CustomerListManager instance;

    public static CustomerListManager getInstance(){
        if (instance == null){
            instance = new CustomerListManager();
        }
        return instance;
    }

    private Context mContext;

    private List<CustomerItemDao> dao;

    private CustomerListManager(){ mContext = Contextor.getInstance().getContext(); }

    public List<CustomerItemDao> getDao() {
        return dao;
    }

    public void setDao(List<CustomerItemDao> dao) {
        this.dao = dao;
    }
}
