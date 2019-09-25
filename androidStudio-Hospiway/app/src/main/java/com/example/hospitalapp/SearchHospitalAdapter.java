package com.example.hospitalapp;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hospitalapp.model.Hospital;
import com.example.hospitalapp.model.IHospitalProvider;
import com.example.hospitalapp.model.TimesHospital;

import java.sql.Time;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SearchHospitalAdapter extends RecyclerView.Adapter<SearchHospitalAdapter.ViewHolder> implements IHospitalProvider.HospitalProviderObserver {

    private List<Hospital> hDataSet;
    private String urgType;

    public static class RecyclerViewItemClickListener implements View.OnClickListener {

        private String hospitalId;
        private String hospitalName;

        public String getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(String hospitalId) {
            this.hospitalId = hospitalId;
        }

        public String getHospitalName() {
            return hospitalName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

        @Override
        public void onClick(View v) {
        }
    }


    protected HospitalListAdapter.RecyclerViewItemClickListener getNewListener() {
        return null; // Needs to be overridden and extended
    }

    public SearchHospitalAdapter(String urgType){
        this.hDataSet = new ArrayList<>();
        this.urgType = urgType;
    }

    public void updateData(List<Hospital> newDataSet){
        List<Hospital> nearHospital = new ArrayList<>();
        for(Hospital hospital: newDataSet){
            if(hospital.hasShareStandByTimes() && hospital.getTimes() != null){
                if(getStandByTimes(urgType, hospital.getTimes()) != null){
                    nearHospital.add(hospital);
                }
            }
        }

        this.hDataSet = nearHospital;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final View hView;
        final TextView hTextViewNome, hTextViewDistance;
        //final TextView hTextViewEsperaGeneral, hTextViewEsperaObstetrical, hTextViewEsperaPediatrics, hTextViewEsperaText;

        ViewHolder(View view) {
            super(view);
            hView = view;
            hTextViewNome = (TextView) view.findViewById(R.id.hospital_list_item_name);
            hTextViewDistance = (TextView) view.findViewById(R.id.hospital_list_item_distance);
            //hTextViewEsperaGeneral = (TextView) view.findViewById(R.id.textViewHospitalWaitGeneral);
            //hTextViewEsperaObstetrical = (TextView) view.findViewById(R.id.hospital_list_item_obstetrical_wait);
            //hTextViewEsperaPediatrics = (TextView) view.findViewById(R.id.hospital_list_item_pediatrics_wait);
            //hTextViewEsperaText = (TextView) view.findViewById(R.id.hospital_list_item_wait_text);
        }
    }

    private TimesHospital getStandByTimes(String type, List<TimesHospital> list) {

        // This strategy mean it'll ignore the accents

        for(TimesHospital sbt : list) {
            if(Normalizer.normalize(
                    sbt.getEmergency().getDescription(),
                    Normalizer.Form.NFD
            ).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").contains(type)) {
                return sbt;
            }
        }
        return null;
    }

    @Override
    public SearchHospitalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_search_hospital, parent, false);
        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(SearchHospitalAdapter.ViewHolder holder, int position) {
        // get element from your dataset at this position
        // replace the contents of the view with that element
        holder.hTextViewNome.setText(hDataSet.get(position).getName());
        //holder.hTextViewMorada.setText(String.valueOf(hDataSet.get(position).getMorada()));
        //holder.hTextViewContacto.setText(hDataSet.get(position).getContacto());

        List<TimesHospital> hospitalStandByTimes = hDataSet.get(position).getTimes();
        Log.d("RENDER", "onBindViewHolder: ");
        if(hospitalStandByTimes != null) {
            //holder.hTextViewEsperaText.setText("Tempos de espera");
            //TimesHospital geral = getStandByTimesFor("Geral" ,hospitalStandByTimes);
            //TimesHospital obstetrica = getStandByTimesFor("Obstetrica" ,hospitalStandByTimes);
            //TimesHospital pediatrica = getStandByTimesFor("Pediatrica" ,hospitalStandByTimes);

            //holder.hTextViewEsperaGeneral.setText(geral == null ? "N/A" : geral.getFormattedTotalStandBytime());
            //holder.hTextViewEsperaObstetrical.setText(obstetrica == null ? "N/A" : obstetrica.getFormattedTotalStandBytime());
            //holder.hTextViewEsperaPediatrics.setText(pediatrica == null ? "N/A" : pediatrica.getFormattedTotalStandBytime());
        }

        // Set click listener
        //holder.hView.setOnClickListener(new SearchHospitalActivity().RecyclerViewItemClickListener( hDataSet.get(position).getId()));

    }

        @Override
        public int getItemCount() {
            if(hDataSet!=null)return hDataSet.size();
            else return 0;
        }
}
