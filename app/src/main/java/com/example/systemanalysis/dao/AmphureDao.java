package com.example.systemanalysis.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AmphureDao {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name_th")
    @Expose
    private String nameTh;
    @SerializedName("name_en")
    @Expose
    private String nameEn;
    @SerializedName("province_id")
    @Expose
    private Integer provinceId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("tombon")
    @Expose
    private List<TombonDao> tombon = null;

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

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
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

    public List<TombonDao> getTombon() {
        return tombon;
    }

    public void setTombon(List<TombonDao> tombon) {
        this.tombon = tombon;
    }

    @Override
    public String toString() {
        return  nameTh ;
    }
}
