package com.example.systemanalysis.manager;

import android.content.Context;

import com.example.systemanalysis.dao.LandItemCollection;
import com.example.systemanalysis.dao.LandItemDao;

import java.util.List;

public class LandListManager {

    private static LandListManager instance;

    public static LandListManager getInstance(){
        if (instance == null){
            instance = new LandListManager();
        }
        return instance;
    }

    private Context mContext;

    private List<LandItemDao> dao;

    private LandListManager(){ mContext = Contextor.getInstance().getContext(); }

    public List<LandItemDao> getDao() {
        return dao;
    }

    public void setDao(List<LandItemDao> dao) {
        this.dao = dao;
    }



}
