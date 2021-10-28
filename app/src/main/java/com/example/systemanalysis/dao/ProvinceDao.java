package com.example.systemanalysis.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProvinceDao {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name_th")
    @Expose
    private String nameTh;
    @SerializedName("name_en")
    @Expose
    private String nameEn;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("amphure")
    @Expose
    private List<AmphureDao> amphure = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNameTh() {
        return nameTh;
    }

    public void setNameTh(String nameTh) {
        this.nameTh = nameTh;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<AmphureDao> getAmphure() {
        return amphure;
    }

    public void setAmphure(List<AmphureDao> amphure) {
        this.amphure = amphure;
    }

    @Override
    public String toString() {
        return nameTh;
    }
}
