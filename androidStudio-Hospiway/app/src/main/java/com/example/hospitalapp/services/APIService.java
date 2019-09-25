package com.example.hospitalapp.services;

import com.example.hospitalapp.model.Hospital;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIService {

    @GET("institution")
    Call<HospitalSearchResponse> getHospitals();

    @GET("standbyTime/{id}")
    Call<HospitalTimesResponse> getStandByTime(@Path("id") String hospitalId);
}
