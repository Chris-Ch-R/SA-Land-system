package com.example.systemanalysis.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TombonDao {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name_th")
    @Expose
    private String nameTh;
    @SerializedName("name_en")
    @Expose
    private String nameEn;
    @SerializedName("zip_code")
    @Expose
    private String zipCode;
    @SerializedName("amphure_id")
    @Expose
    private Integer amphureId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

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

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Integer getAmphureId() {
        return amphureId;
    }

    public void setAmphureId(Integer amphureId) {
        this.amphureId = amphureId;
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

    @Override
    public String toString() {
        return nameTh;
    }
}
