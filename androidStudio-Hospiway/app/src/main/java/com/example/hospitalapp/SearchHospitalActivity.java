package com.example.hospitalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.hospitalapp.model.Hospital;
import com.example.hospitalapp.services.HospitalProvider;

import java.util.List;

public class SearchHospitalActivity extends AppCompatActivity {

    private SearchHospitalAdapter hAdapter;
    private HospitalProvider hHospitalProvider;
    private String urgType;
    private String urgDegree;
    ArrayAdapter<CharSequence> adapter1, adapter2;
    Spinner spinner1;
    Spinner spinner2;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_hospital);


        spinner1 = findViewById(R.id.spinner1);
        adapter1 = ArrayAdapter.createFromResource(this, R.array.array_urgency_type, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);

        spinner2 = findViewById(R.id.spinner2);
        adapter2 = ArrayAdapter.createFromResource(this, R.array.array_urgency_degree, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);



        button = findViewById(R.id.buttonPesquisar);
        //button.setEnabled(true);
        hHospitalProvider = HospitalProvider.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textSpinner1 = spinner1.getSelectedItem().toString();
                String textSpinner2 = spinner2.getSelectedItem().toString();

                Log.d("Button", "Teste butotn:\tSpinner 1: " + urgType + "\tSpinner 2: " + urgDegree);

                List<Hospital> hData = hHospitalProvider.getHospitals();
                String[] info = {textSpinner1, textSpinner2};
                //Intent intent = new Intent(v.getContext(),);
                //intent.putExtra(.stringsForm, info);
                //v.getContext().startActivity(intent);
/*
                for(Hospital hospital: hData){
                    if(hospital.getDistance()<= 60.000 && hospital.hasShareStandByTimes()){
                        HospitalProvider.getInstance().getHospitalTimesAsync(hospital.getId());
                    }
                }*/


            }
        });



    }

    public static class RecyclerViewItemClickListener implements View.OnClickListener {

        private String itemId;

        public RecyclerViewItemClickListener(String itemId) {
            this.itemId = itemId;
        }

        @Override
        public void onClick(View view) {

            // Read more on how to start activities at https://developer.android.com/training/basics/firstapp/starting-activity.html
            Intent intent = new Intent(view.getContext(), HospitalDetailActivity.class);
            intent.putExtra(HospitalDetailActivity.HOSPITAL_ID, itemId);
            view.getContext().startActivity(intent);
        }
    }






}

