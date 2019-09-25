package com.example.hospitalapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hospitalapp.model.Visitor;

import java.util.List;

public class VisitorsAdapter extends RecyclerView.Adapter<VisitorsAdapter.ViewHolder> {

    private List<Visitor> visitorDataSet;

    public static class RecyclerViewItemClickListener implements View.OnClickListener {

        private String visitorId;
        private String visitorName;

        public String getVisitorId() {
            return visitorId;
        }

        public void setVisitorId(String visitorId) {
            this.visitorId = visitorId;
        }

        public String getVisitorName() {
            return visitorName;
        }

        public void setVisitorName(String visitorName) {
            this.visitorName = visitorName;
        }

        @Override
        public void onClick(View v) {
        }
    }

    protected VisitorsAdapter.RecyclerViewItemClickListener getNewListener() {
        return null; // Needs to be overridden and extended
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final View vView;
        final TextView vTextViewName, vtextViewVisitEntryDate, vtextViewVisitExitDate, vtextViewTotalTime;

        ViewHolder(View view) {
            super(view);
            vView = view;
            vTextViewName = (TextView) view.findViewById(R.id.visitor_list_item_name);
            vtextViewVisitEntryDate = (TextView) view.findViewById(R.id.visitor_list_entry_time);
            vtextViewVisitExitDate = (TextView) view.findViewById(R.id.visitor_list_exit_time);
            vtextViewTotalTime = (TextView) view.findViewById(R.id.hospital_list_total_time);
        }
    }

    @Override
    public VisitorsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.visitor_list_item, parent, false);

        return new VisitorsAdapter.ViewHolder(v);
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        // get element from your dataset at this position
        Visitor visitor = visitorDataSet.get(position);
        Log.d("Hospital_Data", "Teste" + visitorDataSet.get(position));
        // replace the contents of the view with that element
        holder.vTextViewName.setText(visitor.getName());
        holder.vtextViewVisitEntryDate.setText(visitor.getEntryDate());
        holder.vtextViewVisitExitDate.setText(visitor.getExitDate());
        holder.vtextViewTotalTime.setText(visitor.getTotalTime());

    }

    @Override
    public int getItemCount() {
        if (visitorDataSet == null) {
            return 0;
        }
        return visitorDataSet.size();
    }

    private Context context;

    public VisitorsAdapter() {
        // EMPTY
    }

    public VisitorsAdapter(Context context, List<Visitor> myDataset) {
        this.context = context;
        this.visitorDataSet = myDataset;
    }
}
