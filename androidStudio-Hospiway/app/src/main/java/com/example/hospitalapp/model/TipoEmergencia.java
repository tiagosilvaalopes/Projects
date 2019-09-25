package com.example.hospitalapp.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class TipoEmergencia extends RealmObject {

    @SerializedName("Code") private String codeEmer;
    @SerializedName("Description") private String description;

    public String getCodeEmer() {
        return codeEmer;
    }

    public void setCodeEmer(String codeEmer) {
        this.codeEmer = codeEmer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
