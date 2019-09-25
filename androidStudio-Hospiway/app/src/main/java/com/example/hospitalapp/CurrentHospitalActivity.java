package com.example.hospitalapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hospitalapp.model.Hospital;
import com.example.hospitalapp.model.IHospitalProvider;
import com.example.hospitalapp.model.TimesHospital;
import com.example.hospitalapp.model.Visitor;
import com.example.hospitalapp.services.GlobalProvider;
import com.example.hospitalapp.services.HospitalProvider;
import com.example.hospitalapp.services.VisitorProviderRealm;

import java.util.Date;
import java.util.List;

public class CurrentHospitalActivity extends AppCompatActivity implements IHospitalProvider.HospitalProviderObserver{
    public static String HOSPITAL_ID = "com.example.hospitalapp.HOSPITAL_ID_EXTRA";

    private Hospital hospital;
    private TextView hTextViewName, hTextViewPhone, hTextViewAddress, hTextViewWebSite, hTextViewDistance, hTextViewMail;
    private TextView hTextViewObsWait, hTextViewGeneralWait, hTextViewPedWait;
    private HospitalProvider hHospitalProvider;
    private VisitorProviderRealm vVisitorProviderRealm;
    private String hospitalId;
    private List<TimesHospital> hospitalStandByTimes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Lifecycle Debug",  this.getLocalClassName() + " - onCreate");

        setContentView(R.layout.activity_current_hospital);

        this.hTextViewName = (TextView) this.findViewById(R.id.textViewHospitalDetailName);
        this.hTextViewPhone = findViewById(R.id.textViewHospitalPhone);
        this.hTextViewAddress = findViewById(R.id.textViewHospitalAddress);
        this.hTextViewWebSite = (TextView) this.findViewById(R.id.textViewHospitalWebSite);
        this.hTextViewMail = (TextView) this.findViewById(R.id.textViewHospitalMail);
        this.hTextViewDistance = (TextView) this.findViewById(R.id.textViewHospitalDistance);
        this.hTextViewGeneralWait = (TextView) this.findViewById(R.id.textViewHospitalWaitGeneral);
        this.hTextViewPedWait = (TextView) this.findViewById(R.id.textViewHospitalWaitPed);
        this.hTextViewObsWait = (TextView) this.findViewById(R.id.textViewHospitalWaitObs);

        hHospitalProvider = HospitalProvider.getInstance();
        vVisitorProviderRealm = VisitorProviderRealm.getInstance(this);

        // Get parameter from previous screen
        final String hospitalId = getIntent().getStringExtra(HospitalDetailActivity.HOSPITAL_ID);
        Log.d("Activity Extra",  "Hospital param : " + hospitalId);

        if (((GlobalProvider) this.getApplication()).getCheckedInHospital() != null) {
            hHospitalProvider = HospitalProvider.getInstance();

            this.hospital = ((GlobalProvider) this.getApplication()).getCheckedInHospital();
            this.makeHospital();

            // specify an adapter and pass data-set
            if(hospital.hasShareStandByTimes()) {
                hHospitalProvider.getHospitalStandByTimeAsync(hospitalId);
                hospitalStandByTimes = hospital.getTimes();
                hHospitalProvider.addObserver(this);
            }
        }
    }

    public void doCheckIn(View view){
        hospital.setEntryTime(new Date());
        Toast.makeText(this, "Bem-Vindo!", Toast.LENGTH_LONG).show();
    }

    public void doOnWay(View view){
        Toast.makeText(this, "Notificámos que está a caminho", Toast.LENGTH_LONG).show();
    }

    public void doCheckOut(View view){
        if(hospital.getEntryTime() != null){
            hospital.setExitTime(new Date());
            Visitor visitor = new Visitor(hospital.getName(), hospital.getEntryTime(), hospital.getExitTime());
            vVisitorProviderRealm.addVisitor(visitor);

            hospital.setEntryTime(null);
            hospital.setExitTime(null);
            ((GlobalProvider) this.getApplication()).checkoutHospital();
            Toast.makeText(this, "Obrigado! Desejamos as melhoras!", Toast.LENGTH_LONG).show();
            return;
        }

        Toast.makeText(this, "Não pode fazer check-out", Toast.LENGTH_LONG).show();
    }



    private void makeHospital() {
        hTextViewName.setText(this.hospital.getName());
        hTextViewAddress.setText(this.hospital.getAddress());
        hTextViewPhone.setText(String.valueOf(this.hospital.getPhone()));
        hTextViewWebSite.setText(this.hospital.getInstitutionURL());
        hTextViewMail.setText(this.hospital.getEmail());
        hTextViewDistance.setText(String.format("%.2f", hospital.getDistance()) + " km");

        if(hospitalStandByTimes != null) {
            this.hTextViewGeneralWait.setText(hospital.getTimeText("Geral"));
            this.hTextViewObsWait.setText(hospital.getTimeText("Obstetrica"));
            this.hTextViewPedWait.setText(hospital.getTimeText("Pediatrica"));
        } else {
            Log.d("test", "standbytimes null ");
            this.hTextViewGeneralWait.setText("N/A");
            this.hTextViewObsWait.setText("N/A");
            this.hTextViewPedWait.setText("N/A");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
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

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.d("Lifecycle State Debug", this.getLocalClassName() + " - onSaveInstanceState");
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }

    public void openWebSiteExternalPage(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(hospital.getInstitutionURL()));
        startActivity(intent);
    }

    public void openAddressExternalPage(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://maps.google.com/maps?saddr=" + hospital.getAddress()));
        startActivity(intent);
    }
    public void openPhoneExternalPage(View view){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(String.valueOf("tel:" + hospital.getPhone())));
        startActivity(intent);
    }
    public void openMailExternalPage(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("mailto:" + hospital.getEmail()));
        startActivity(intent);
    }

    @Override
    public void updateData(List<Hospital> newDataSet) {
        for(Hospital h : newDataSet) {
            if(h.getId().equals(hospitalId)) {
                hospitalStandByTimes = h.getTimes();
            }
        }

        makeHospital();
    }

    public void refreshTimes(View view){
        if(hospital.hasShareStandByTimes()) {
            Toast.makeText(this, "A atualizar os tempos de espera...", Toast.LENGTH_LONG).show();
            hHospitalProvider.getHospitalStandByTimeAsync(hospitalId);
            hospitalStandByTimes = hospital.getTimes();
            hHospitalProvider.addObserver(this);
            makeHospital();
        }else {
            Toast.makeText(this, "Hospital sem tempos de espera disponíveis", Toast.LENGTH_LONG).show();
        }
    }



}
