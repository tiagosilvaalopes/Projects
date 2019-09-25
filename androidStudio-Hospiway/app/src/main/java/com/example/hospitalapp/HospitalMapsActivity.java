package com.example.hospitalapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.hospitalapp.model.Hospital;
import com.example.hospitalapp.services.HospitalProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class HospitalMapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private static final String markerMe = "me";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);

        if (ContextCompat.checkSelfPermission( this ,Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED ) {

            mMap.setMyLocationEnabled(true);
        }

        final List<Hospital> hospitals = HospitalProvider.getInstance().getHospitals();
        Log.d("lol", "Teste" + hospitals);

        for (final Hospital hospital : hospitals){
            LatLng current = new LatLng(hospital.getLatitude(), hospital.getLongitude());
            mMap.addMarker(new MarkerOptions().position(current).title(hospital.getId()));
            Log.d("Marker", "Teste" + hospital.getId());
        }
        if(!hospitals.isEmpty()){
            LatLng currentL = new LatLng(HospitalProvider.getInstance().getThisLocation().getLatitude(), HospitalProvider.getInstance().getThisLocation().getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentL, 12.5f));
        } else {
            throw new IllegalStateException("Esta vazio");
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker){
        if(!marker.getTitle().equals(markerMe)){
            Intent intent = new Intent(this, HospitalDetailActivity.class);
            intent.putExtra(HospitalDetailActivity.HOSPITAL_ID, marker.getTitle());
            startActivity(intent);
        }
        return true;
    }
}
