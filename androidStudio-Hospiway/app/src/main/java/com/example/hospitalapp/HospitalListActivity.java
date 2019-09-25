package com.example.hospitalapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hospitalapp.services.GlobalProvider;
import com.example.hospitalapp.services.HospitalProvider;

public class HospitalListActivity extends AppCompatActivity {
    private final int REFRESH_TIME = 1;
    private final int REFRESH_DISTANCE = 1;
    public static Location thisLocation;
    public static LocationManager locationManager;
    private BottomNavigationView bottomNavigationView;
    private static HospitalProvider hHospitalProvider;
    private HospitalListAdapter hAdapter;
    private VisitorsAdapter vAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private int checkRequest;


    public static class ListRecyclerViewItemClickListener extends HospitalListAdapter.RecyclerViewItemClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), HospitalDetailActivity.class);
            Log.d("ON_CLICK", "Hospital ID: " + getHospitalId() + ", Hospital Name: " + getHospitalName());
            intent.putExtra(HospitalDetailActivity.HOSPITAL_ID, getHospitalId());
            view.getContext().startActivity(intent);
        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            if(item == null) return false;
            Log.d("History" , "TESTE" + item.getItemId());
            return loadTabData(item.getItemId());
        }
    };

    private boolean loadTabData(int tabId) {

        switch (tabId) {

            case R.id.tab_navigation_home:
                hAdapter.updateData(hHospitalProvider.getHospitals());
                return true;
            case R.id.tab_navigation_search:
                startActivity(new Intent(getApplicationContext(), SearchHospitalActivity.class));
                return true;
            case R.id.tab_navigation_history:
                startActivity(new Intent(getApplicationContext(), HistoryHospitalActivity.class));
                return true;
            case R.id.tab_navigation_map:
                startActivity(new Intent(getApplicationContext(), HospitalMapsActivity.class));
                return true;
            case R.id.tab_navigation_current_hospital:
                if(((GlobalProvider) this.getApplication()).isCheckedIn() || ((GlobalProvider) this.getApplication()).isOnTheWay()){
                    startActivity(new Intent(getApplicationContext(), CurrentHospitalActivity.class));
                } else {
                    Toast.makeText(this, "Página indisponíel. Você não se encontra em/a caminho de nenhum hospital", Toast.LENGTH_LONG).show();
                }

        }
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Lifecycle Debug", this.getLocalClassName() + " - onCreate");

        setContentView(R.layout.activity_hospital_list_tabs);


        // Set listener to bottom tabbed navigation
        bottomNavigationView = findViewById(R.id.navigation_hospital_list);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        RecyclerView hRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_hospital_list);
        // use a linear layout manager
        RecyclerView.LayoutManager hLayoutManager = new LinearLayoutManager(this);
        hRecyclerView.setLayoutManager(hLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(hRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        hRecyclerView.addItemDecoration(itemDecoration);


        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, checkRequest);

        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, REFRESH_TIME, REFRESH_DISTANCE, locationEscutador);

        // specify an adapter and pass data-set
        hHospitalProvider = HospitalProvider.getInstance();
        hHospitalProvider.searchHospitalsAsync();
        //setRefreshState(true);
        hAdapter = new HospitalListAdapter(this, hHospitalProvider.getHospitals()) {
            @Override
            protected RecyclerViewItemClickListener getNewListener() {
                return new ListRecyclerViewItemClickListener();
            }
        };


        hHospitalProvider.addObserver(hAdapter);
        hRecyclerView.setAdapter(hAdapter);


    }

    /*public void setRefreshState(boolean refreshState) {
        mSwipeRefreshLayout.setRefreshing(refreshState);
    }*/

    @Override
    protected void onStart() {
        super.onStart();

        loadTabData(bottomNavigationView.getSelectedItemId());

        Log.d("Lifecycle Debug", this.getLocalClassName() + " - onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Lifecycle Debug", this.getLocalClassName() + " - onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Lifecycle Debug", this.getLocalClassName() + " - onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Lifecycle Debug", this.getLocalClassName() + " - onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Lifecycle Debug", this.getLocalClassName() + " - onDestroy");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("Lifecycle State Debug", this.getLocalClassName() + " - onRestoreInstanceState");

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("Lifecycle State Debug", this.getLocalClassName() + " - onSaveInstanceState");

        super.onSaveInstanceState(outState);
    }

    public static final LocationListener locationEscutador = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            thisLocation = location;
            hHospitalProvider.setThisLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}
