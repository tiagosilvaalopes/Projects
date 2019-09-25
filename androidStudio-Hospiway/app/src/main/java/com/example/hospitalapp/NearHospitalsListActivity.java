/*package com.example.hospitalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.hospitalapp.model.Hospital;
import com.example.hospitalapp.services.HospitalProvider;

import java.util.List;

public class NearHospitalsListActivity extends AppCompatActivity {

    private HospitalProvider hospitalProvider;
    private SearchHospitalAdapter searchHospitalAdapter;
    public static String stringsForm = "" ;
    private String urgType ;
    private String urgDegree ;

    public static class ListRecyclerViewItemClickListener extends HospitalListAdapter.RecyclerViewItemClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), HospitalDetailActivity.class);
            Log.d("ON_CLICK", "Hospital ID: " + getHospitalId() + ", Hospital Name: " + getHospitalName());
            intent.putExtra(HospitalDetailActivity.HOSPITAL_ID, getHospitalId());
            view.getContext().startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_hospital);

        String info[] = getIntent().getStringArrayExtra(stringsForm);
        urgType = info[0];
        urgDegree = info[1];

        RecyclerView hRecyclerView = (RecyclerView) findViewById(R.id.hospitals_list_search);

        RecyclerView.LayoutManager hLayoutManager = new LinearLayoutManager(this);
        hRecyclerView.setLayoutManager(hLayoutManager);

        hospitalProvider = HospitalProvider.getInstance();
        List<Hospital> hospitalD = hospitalProvider.getHospitals();
        int count = 0;
        for (Hospital hospital: hospitalD){
            count += 1;
            if(hospital.hasShareStandByTimes() && count < 3){
                HospitalProvider.getInstance().getHospitalStandByTimeAsync(hospital.getId());
            }
        }

        searchHospitalAdapter = new SearchHospitalAdapter(urgType);
        hospitalProvider.addObserver(searchHospitalAdapter);
        hRecyclerView.setAdapter(searchHospitalAdapter);

    }


}*/
