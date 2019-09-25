package com.example.hospitalapp.services;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.example.hospitalapp.model.Hospital;
import com.example.hospitalapp.model.Visitor;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class VisitorProviderRealm {

    private static VisitorProviderRealm _instance;

    private VisitorProviderRealm() {
    }

    public static VisitorProviderRealm getInstance(Context context) {
        if (_instance == null) {
            _instance = new VisitorProviderRealm();
            _instance.initializeData(context);
        }
        return _instance;
    }

    private static final String REALM_ASSET_FILEPATH = "visits_import.realm"; // Path to Realm file in assets folder
    private static final String REALM_FILENAME = "visitors_data_v001.realm"; // Name for app internal Real file

    private static final boolean SEARCH_IN_MEMORY = false;
    private Realm realm;
    protected List<Visitor> visitors;

    public void initializeData(Context context) {

        // Initialize Realm (just once per application)
        Realm.init(context);

        // Create a custom configuration
        // Read more on https://realm.io/docs/java/latest/api/io/realm/RealmConfiguration.Builder.html
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name(REALM_FILENAME) // Name to be used internally
                .assetFile(REALM_ASSET_FILEPATH) // Copy Realm file from this location, searching in Android asset folder
                .schemaVersion(0) // If Realm Objects change, schema version needs to be increased
                .build();

        // Get instance
        realm = Realm.getInstance(realmConfig);
        Log.d("REALM DEBUG", "Realm file path: " + realm.getPath() + ", version: "
                + realm.getVersion() + " is " + ((!realm.isEmpty())?"not ":"") + "empty");

        // Get all items from given class
        RealmResults<Visitor> visitorRealmReasult = realm.where(Visitor.class).findAll();
        Log.d("REALM DEBUG", "Read from " + visitorRealmReasult.size() + " records from Realm file.");

        // Save results to class instance ArrayList (to abstract from Realm)
        visitors = new ArrayList<Visitor>( visitorRealmReasult );

    }

    public void beginTransaction() {
        realm.beginTransaction();
    }

    public void commitTransaction() {
        realm.commitTransaction();
    }

    public void addVisitor(Visitor visitor) {
        // Persist data in a transaction
        Log.d("REALM DEBUG", "Transaction Init for adding hospital'" + visitor.getName() + "'");

        realm.beginTransaction();
        // Persist unmanaged object
        realm.copyToRealm(visitor);
        // Commit transaction
        realm.commitTransaction();
        Log.d("REALM DEBUG", "Transaction Committed");

        // Add visitor to in-memory array
        visitors.add(visitor);
    }

    public List<Visitor> getVisitors() {
        return visitors;
    }


}
