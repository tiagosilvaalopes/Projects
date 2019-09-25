package com.example.hospitalapp.model;

import java.util.List;

public interface IHospitalProvider {
    // Provider data methods
    List<Hospital> getHospitals();
    Hospital getHospital(String hospitalId);

    // Provider Observable methods
    void addObserver(HospitalProviderObserver observer);
    void removeObserver(HospitalProviderObserver observer);
    void notifyObserverDataChanged();

    // Interface for Observers
    interface HospitalProviderObserver {
        void updateData(List<Hospital> newDataSet);
    }
}
