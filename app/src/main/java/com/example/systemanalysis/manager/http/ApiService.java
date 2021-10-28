package com.example.systemanalysis.manager.http;

import com.example.systemanalysis.dao.AdsItemDao;
import com.example.systemanalysis.dao.AgreementItemDao;
import com.example.systemanalysis.dao.ConsignmentItemDao;
import com.example.systemanalysis.dao.CustomerItemDao;
import com.example.systemanalysis.dao.LandItemCollection;
import com.example.systemanalysis.dao.LandItemDao;
import com.example.systemanalysis.dao.ProvinceDao;
import com.example.systemanalysis.dao.TombonDao;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface ApiService {

    @GET("land")
    Call<List<LandItemDao>> loadLandList();

    @GET("customer")
    Call<List<CustomerItemDao>> loadCustomerList();

    @GET("consignment")
    Call<List<ConsignmentItemDao>> loadConsignmentList();

    //TODO customer land_id
    @GET("customer/{id}")
    Call<CustomerItemDao> loadCustomerDetail(@Path("id") int customerID);

    //TODO ads_landID
    @GET("ads/{id}")
    Call<List<AdsItemDao>> loadAdsList(@Path("id") int landID);

    @GET("ads")
    Call<List<AdsItemDao>> loadAdsList2();

    @GET("all/province")
    Call<List<ProvinceDao>> loadProvince();

    @GET("province")
    Call<List<ProvinceDao>> loadProvince2();

    @GET("tombon")
    Call<List<TombonDao>> loadTombon();

    @POST("consignment")
    Call<ConsignmentItemDao> addConsignor(@Body ConsignmentItemDao consignor);

    @POST("customer")
    Call<CustomerItemDao> addCustomer(@Body CustomerItemDao customer);

    @Multipart
    @POST("land")
    Call<LandItemDao> addLand(@PartMap LandItemDao land);

    @POST("agreement")
    Call<AgreementItemDao> addAgreement(@Body AgreementItemDao agreement);


}
