package com.example.hospitalapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hospitalapp.model.Hospital;
import com.example.hospitalapp.model.IHospitalProvider;
import com.example.hospitalapp.services.APIService;
import com.example.hospitalapp.services.HospitalProvider;

import java.util.ArrayList;
import java.util.List;

public class HospitalListAdapter extends RecyclerView.Adapter<HospitalListAdapter.ViewHolder>
        implements HospitalProvider.HospitalProviderObserver {

    private List<Hospital> hospitalDataSet;

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

    protected RecyclerViewItemClickListener getNewListener() {
        return null; // Needs to be overridden and extended
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        final View hView;
        final TextView hTextViewName, hTextViewDistance;

        ViewHolder(View view) {
            super(view);
            hView = view;
            hTextViewName = (TextView) view.findViewById(R.id.hospital_list_item_name);
            hTextViewDistance = (TextView) view.findViewById(R.id.hospital_list_item_distance);
        }
    }

    @Override
    public HospitalListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.hospital_list_item, parent, false);

        return new ViewHolder(v);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        // get element from your dataset at this position
        Hospital hospital = hospitalDataSet.get(position);
        Log.d("Hospital_Data", "Teste" + hospitalDataSet.get(position));
        // replace the contents of the view with that element
        holder.hTextViewName.setText(hospital.getName());
        holder.hTextViewDistance.setText(String.format("%.2f", hospital.getDistance()) + " km");



        RecyclerViewItemClickListener onClickListener = getNewListener();
        onClickListener.setHospitalId(hospital.getId());
        onClickListener.setHospitalName(hospital.getName());
        holder.hView.setOnClickListener(onClickListener);

    }

    @Override
    public int getItemCount() {
        if (hospitalDataSet == null) {
            return 0;
        }
        return hospitalDataSet.size();
    }

    private Context context;

    public HospitalListAdapter() {
        // EMPTY
    }

    public HospitalListAdapter(Context context) {
        this.context = context;
    }


    public HospitalListAdapter(Context context, List<Hospital> myDataset) {
        this.context = context;
        this.hospitalDataSet = myDataset;
    }


    public void updateData(List<Hospital> newDataSet) {
        Log.d(this.getClass().toString(), "Updating Adapter data with " + (newDataSet!=null?newDataSet.size():0) + " items");
        this.hospitalDataSet = newDataSet;
        notifyDataSetChanged();
    }


}
