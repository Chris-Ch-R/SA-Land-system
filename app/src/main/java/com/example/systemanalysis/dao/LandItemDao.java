package com.example.systemanalysis.dao;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LandItemDao {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("owner_id")
    @Expose
    private Integer ownerId;
    @SerializedName("tombon_id")
    @Expose
    private Integer tombonId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("land_number")
    @Expose
    private String landNumber;
    @SerializedName("deed_number")
    @Expose
    private String deedNumber;
    @SerializedName("land_address")
    @Expose
    private String landAddress;
    @SerializedName("land_area_real")
    @Expose
    private Integer landAreaReal;
    @SerializedName("land_area_deed")
    @Expose
    private Integer landAreaDeed;
    @SerializedName("price_per_unit")
    @Expose
    private Integer pricePerUnit;
    @SerializedName("type_by_color")
    @Expose
    private String typeByColor;
    @SerializedName("commission")
    @Expose
    private Double commission;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("owner")
    @Expose
    private ConsignmentItemDao owner;
    @SerializedName("agreement")
    @Expose
    private Object agreement;
    @SerializedName("ads")
    @Expose
    private List<AdsItemDao> ads = null;
    @SerializedName("land_file")
    @Expose
    private List<LandFileDao> landFile = null;
    @SerializedName("tombon")
    @Expose
    private TombonDao tombon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getTombonId() {
        return tombonId;
    }

    public void setTombonId(Integer tombonId) {
        this.tombonId = tombonId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLandNumber() {
        return landNumber;
    }

    public void setLandNumber(String landNumber) {
        this.landNumber = landNumber;
    }

    public String getDeedNumber() {
        return deedNumber;
    }

    public void setDeedNumber(String deedNumber) {
        this.deedNumber = deedNumber;
    }

    public String getLandAddress() {
        return landAddress;
    }

    public void setLandAddress(String landAddress) {
        this.landAddress = landAddress;
    }

    public Integer getLandAreaReal() {
        return landAreaReal;
    }

    public void setLandAreaReal(Integer landAreaReal) {
        this.landAreaReal = landAreaReal;
    }

    public Integer getLandAreaDeed() {
        return landAreaDeed;
    }

    public void setLandAreaDeed(Integer landAreaDeed) {
        this.landAreaDeed = landAreaDeed;
    }

    public Integer getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(Integer pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public String getTypeByColor() {
        return typeByColor;
    }

    public void setTypeByColor(String typeByColor) {
        this.typeByColor = typeByColor;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }

    public ConsignmentItemDao getOwner() {
        return owner;
    }

    public void setOwner(ConsignmentItemDao owner) {
        this.owner = owner;
    }

    public Object getAgreement() {
        return agreement;
    }

    public void setAgreement(Object agreement) {
        this.agreement = agreement;
    }

    public List<AdsItemDao> getAds() {
        return ads;
    }

    public void setAds(List<AdsItemDao> ads) {
        this.ads = ads;
    }

    public List<LandFileDao> getLandFile() {
        return landFile;
    }

    public void setLandFile(List<LandFileDao> landFile) {
        this.landFile = landFile;
    }

    public TombonDao getTombon() {
        return tombon;
    }

    public void setTombon(TombonDao tombon) {
        this.tombon = tombon;
    }
}
