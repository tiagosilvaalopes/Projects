package com.example.hospitalapp.services;

import android.location.Location;
import android.util.Log;

import com.example.hospitalapp.model.Hospital;
import com.example.hospitalapp.model.HospitalProviderAbstract;
import com.example.hospitalapp.model.TimesHospital;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HospitalProvider extends HospitalProviderAbstract {

    private static HospitalProvider _instance;
    private Location thisLocation;
    private HospitalProvider() {
    }

    public static HospitalProvider getInstance() {
        if (_instance == null) {
            _instance = new HospitalProvider();
            _instance.init();
        }
        return _instance;
    }

    public static final String API_BASE_URL = "http://tempos.min-saude.pt/api.php/";

    private APIService apiService;

    private void init() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(APIService.class);

    }

    public void sortHospitais() {
        Collections.sort(hospitalData, new Comparator<Hospital>() {
            @Override
            public int compare(Hospital h1, Hospital h2) {
                if(h1.getDistance() > h2.getDistance())return 1;
                if (h1.getDistance() < h2.getDistance())return -1;
                return 0;
            }
        });
    }

    public void searchHospitalsAsync() {
        Call<HospitalSearchResponse> call = apiService.getHospitals();
        call.enqueue(new Callback<HospitalSearchResponse>() {
            @Override
            public void onResponse(Call<HospitalSearchResponse> call, Response<HospitalSearchResponse> response) {
                List<Hospital> hospitals = response.body().getHospitals();
                hospitalData = hospitals;
                //notifyObserverDataChanged();
                Log.d("HOSPITAL_RESPONSE", "Hospital List Size: " + hospitalData.size());
                Log.d("Location", "AQui" + thisLocation);
                calculateDistance();
                sortHospitais();

                //realm.beginTransaction();
                //realm.copyToRealmOrUpdate(hospitalData);
                //realm.commitTransaction();
                notifyObserverDataChanged();
            }


            @Override
            public void onFailure(Call<HospitalSearchResponse> call, Throwable t) {

                notifyObserverDataChanged();
            }
        });
    }

    public void getHospitalStandByTimeAsync(final String hospitalId) {

        String id = hospitalId;
        Call<HospitalTimesResponse> call = apiService.getStandByTime(hospitalId);
        Log.d("getHospSBTime", "passei a chamada");

        call.enqueue(new Callback<HospitalTimesResponse>() {
            @Override
            public void onResponse(Call<HospitalTimesResponse> call, Response<HospitalTimesResponse> response) {
                List<TimesHospital> times = response.body().getTimes();
                Log.d("RETROFIT,search", "Number of times received: " + (times != null ? times.size() : 0));
                getHospital(hospitalId).setTimes(times);
                //saveToRealm();

                notifyObserverDataChanged();

            }


            @Override
            public void onFailure(Call<HospitalTimesResponse> call, Throwable throwable) {
                Log.e("RETROFIT12", "DEU ");
                Log.e("RETROFIT", throwable.toString());
                //Toast.makeText(HospitalProvider.this, "Não foi possível obter os tempos de espera", Toast.LENGTH_LONG).show();
                //saveToRealm();
                notifyObserverDataChanged();

            }

        });

    }

    @Override
    public List<Hospital> getHospitals(){
        return hospitalData;
    }

    @Override
    public Hospital getHospital(String hospitalId){
        for(Hospital h : hospitalData){
            if(h.getId().equals(hospitalId)){
                return h;
            }
        }
        return null;
    }

    public void calculateDistance(){
        if(thisLocation == null || hospitalData == null) return;
        for(Hospital h : hospitalData){
            h.setDistance(h.calculateDistance(thisLocation));
        }
    }

    public void setThisLocation(Location thisLocation) {
        this.thisLocation = thisLocation;
    }

    public Location getThisLocation() {
        return thisLocation;
    }


}
