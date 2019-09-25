package com.example.hospitalapp.services;

import com.example.hospitalapp.model.TimesHospital;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HospitalTimesResponse {

    @SerializedName("Result")
    private List<TimesHospital> times;

    public List<TimesHospital> getTimes(){
        return times;
    }

    public void setTimes(List<TimesHospital> times) {
        this.times = times;
    }
}
