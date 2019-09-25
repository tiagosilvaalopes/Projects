package com.example.hospitalapp.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class FilaEmergencia extends RealmObject {

    @SerializedName("Time") private int waitingTime;
    @SerializedName("Length") private int filaLength;

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getFilaLength() {
        return filaLength;
    }

    public void setFilaLength(int filaLength) {
        this.filaLength = filaLength;
    }
}
