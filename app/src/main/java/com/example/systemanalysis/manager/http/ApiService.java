package com.example.systemanalysis.manager.http;

import com.example.systemanalysis.dao.AdsItemDao;
import com.example.systemanalysis.dao.AgreementItemDao;
import com.example.systemanalysis.dao.ConsignmentItemDao;
import com.example.systemanalysis.dao.CustomerItemDao;
import com.example.systemanalysis.dao.LandFileDao;
import com.example.systemanalysis.dao.LandItemCollection;
import com.example.systemanalysis.dao.LandItemDao;
import com.example.systemanalysis.dao.ProvinceDao;
import com.example.systemanalysis.dao.TombonDao;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface ApiService {

    @GET("land")
    Call<List<LandItemDao>> loadLandList();

    @GET("land/{id}")
    Call<LandItemDao> loadInterestedList(@Path("id") int landID);

    @GET("customer")
    Call<List<CustomerItemDao>> loadCustomerList();

    @GET("consignment")
    Call<List<ConsignmentItemDao>> loadConsignmentList();

    @GET("customer/{id}")
    Call<CustomerItemDao> loadCustomerDetail(@Path("id") int customerID);

    //TODO ads_landID
    @GET("ads/{id}")
    Call<List<AdsItemDao>> loadAdsList(@Path("id") int landID);

    @GET("ads")
    Call<List<AdsItemDao>> loadAdsList2();

    @GET("province")
    Call<List<ProvinceDao>> loadProvince2();

    @GET("amphure/tombon/{id}")
    Call<List<TombonDao>> loadTombon(@Path("id") String amphureID);

    @POST("consignment")
    Call<ConsignmentItemDao> addConsignor(@Body ConsignmentItemDao consignor);

    @POST("ads")
    Call<AdsItemDao> addAds(@Body AdsItemDao ads);

    @POST("customer")
    Call<CustomerItemDao> addCustomer(@Body CustomerItemDao customer);

    @Multipart
    @POST("land")
    Call<ResponseBody> addLand(
            @Part("owner_id") RequestBody consignmentId,
            @Part("tombon_id") RequestBody tombonId,
            @Part("land_number") RequestBody landNumber,
            @Part("deed_number") RequestBody deedNumber,
            @Part("land_address") RequestBody landAddres,
            @Part("land_area_real") RequestBody landAreaReal,
            @Part("land_area_deed") RequestBody landAreaDeed,
            @Part("price_per_unit") RequestBody pricePerUnit,
            @Part("type_by_color") RequestBody typeByColor,
            @Part("commission") RequestBody commission,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude,
            @Part MultipartBody.Part image,
            //agreement
            @Part("deadline") RequestBody deadline,
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("customeragreement")
    Call<ResponseBody> addCustomerAgreement(
            @Part("land_id") RequestBody landID,
            @Part("customer_id") RequestBody customerID,
            //agreement
            @Part MultipartBody.Part file
    );

    @Multipart
    @POST("customeragreement")
    Call<ResponseBody> changeStatus(
            @Part("land_id") RequestBody landID,
            @Part("customer_id") RequestBody customerID,
            @Part("status") RequestBody status
    );

}
