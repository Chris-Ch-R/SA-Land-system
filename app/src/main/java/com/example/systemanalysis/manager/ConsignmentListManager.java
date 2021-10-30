package com.example.systemanalysis.manager;

import android.content.Context;

import com.example.systemanalysis.dao.ConsignmentItemDao;
import com.example.systemanalysis.dao.CustomerItemDao;

import java.util.List;

public class ConsignmentListManager {

    private static ConsignmentListManager instance;

    public static ConsignmentListManager getInstance(){
        if (instance == null){
            instance = new ConsignmentListManager();
        }
        return instance;
    }

    private Context mContext;

    private List<ConsignmentItemDao> dao;

    private ConsignmentListManager(){ mContext = Contextor.getInstance().getContext(); }

    public List<ConsignmentItemDao> getDao() {
        return dao;
    }

    public void setDao(List<ConsignmentItemDao> dao) {
        this.dao = dao;
    }


}
