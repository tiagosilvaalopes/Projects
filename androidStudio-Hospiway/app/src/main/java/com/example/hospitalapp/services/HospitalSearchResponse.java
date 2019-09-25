package com.example.hospitalapp.services;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.example.hospitalapp.model.Hospital;

public class HospitalSearchResponse {

    @SerializedName("Result")
    private List<Hospital> hospitals;

    public List<Hospital> getHospitals() {
        return hospitals;
    }

    public void setHospitals(List<Hospital> hospitals) {
        this.hospitals = hospitals;
    }

}
