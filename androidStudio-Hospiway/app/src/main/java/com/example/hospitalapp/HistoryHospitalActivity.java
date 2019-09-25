package com.example.hospitalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.hospitalapp.services.VisitorProviderRealm;

public class HistoryHospitalActivity extends AppCompatActivity {

    private VisitorProviderRealm hVisitorProvider;
    private VisitorsAdapter hAdapter;

    public static class RecyclerViewItemClickListener implements View.OnClickListener {

        private int itemId;

        public RecyclerViewItemClickListener(int itemId) {
            this.itemId = itemId;
        }

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(view.getContext(), HospitalDetailActivity.class);
            intent.putExtra(HospitalDetailActivity.HOSPITAL_ID, Integer.toString(itemId));
            view.getContext().startActivity(intent);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Lifecycle Debug", this.getLocalClassName() + " - onCreate" );

        setContentView(R.layout.activity_hospital_history);

        // Read more about Recycler View at https://developer.android.com/guide/topics/ui/layout/recyclerview.html#java
        RecyclerView hRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_hospital_list);
        // use a linear layout manager
        RecyclerView.LayoutManager hLayoutManager = new LinearLayoutManager(this);
        hRecyclerView.setLayoutManager(hLayoutManager);

        // specify an adapter and pass data-set
        hVisitorProvider = VisitorProviderRealm.getInstance(this);
        hAdapter = new VisitorsAdapter(this, hVisitorProvider.getVisitors());
        hRecyclerView.setAdapter(hAdapter);

    }

}
