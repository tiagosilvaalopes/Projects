package com.example.hospitalapp.services;

import android.annotation.SuppressLint;
import android.app.Application;
import android.location.Location;
import android.util.Log;

import com.example.hospitalapp.model.Hospital;


public class GlobalProvider extends Application {

    private Location currentLocation;
    private boolean isCheckedIn;
    private boolean isOnTheWay;
    private Hospital checkedInHospital;
    private String corSenha;
    private int numSenha;
    public static GlobalProvider _instance;

    public static GlobalProvider getInstance() {

        if(_instance == null){
            Log.d("Global Provider", "Create from scratch");
            _instance = new GlobalProvider();

        }
        return _instance;
    }
    @SuppressLint("LongLogTag")
    public Location getCurrentLocation() {
        Log.d("Global Provider Current location on get: ", currentLocation.toString());
        Log.d("Global provider", "GET");
        return currentLocation;
    }

    @SuppressLint("LongLogTag")
    public void setCurrentLocation(Location currentLocation) {
        Log.d("Global provider", "SET");
        this.currentLocation = currentLocation;
        Log.d("Global Provider Current location after set: ", currentLocation.toString());
    }

    public boolean isCheckedIn() {
        Log.d("GLOBAL PROVIDER", "isCheckedIn");
        return isCheckedIn;
    }

    public void setCheckedIn(boolean checkedIn) {
        Log.d("GLOBAL PROVIDER", "setCheckedIn");
        isCheckedIn = checkedIn;
    }

    public boolean isOnTheWay() {
        return isOnTheWay;
    }

    public void setOnTheWay(boolean onTheWay) {
        isOnTheWay = onTheWay;
    }

    public Hospital getCheckedInHospital() {
        return checkedInHospital;
    }

    public void setCheckedInHospital(Hospital checkedInHospital) {
        Log.d("GLOBAL PROVIDER", "CheckedIn Hospital " + checkedInHospital.getName());
        this.checkedInHospital = checkedInHospital;
    }

    public void checkoutHospital(){
        this.setCheckedIn(false);
        Log.d("GLOBAL PROVIDER", "Checkedout Hospital");
        this.checkedInHospital = null;
    }

    public String getCorSenha() {
        return corSenha;
    }

    public void setCorSenha(String corSenha) {
        this.corSenha = corSenha;
    }

    public int getNumSenha() {
        return numSenha;
    }

    public void setNumSenha(int numSenha) {
        this.numSenha = numSenha;
    }
}

