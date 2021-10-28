package com.example.systemanalysis.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LandItemCollection {
//    @SerializedName("data")
//    @Expose
    private List<LandItemDao> data;

    public List<LandItemDao> getData() {
        return data;
    }

    public void setData(List<LandItemDao> data) {
        this.data = data;
    }

}
