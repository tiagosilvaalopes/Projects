package com.example.hospitalapp.model;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

public class TimesHospital extends RealmObject{

    @SerializedName("Blue") private FilaEmergencia blue;
    @SerializedName("Green") private FilaEmergencia green;
    @SerializedName("Red") private FilaEmergencia red;
    @SerializedName("Orange") private FilaEmergencia orange;
    @SerializedName("Yellow") private FilaEmergencia yellow;

    @SerializedName("Emergency") private TipoEmergencia emergency;

    public int getAllTimes(){
        int allTime = blue.getWaitingTime()
                + green.getWaitingTime()
                + red.getWaitingTime()
                + orange.getWaitingTime()
                + yellow.getWaitingTime();

        return allTime;
    }

    public String getNormalTime() {
        int total= getAllTimes();
        int hours = total/ 60 / 60;
        int minutes = total/ 60 - hours * 60;
        //int seconds = totalSBTime - minutes * 60 - hours * 60 * 60;

        if(hours >= 0) { // short time
            return String.format("%dm", minutes);
        } else {
            return String.format("%dh%02dm", hours, minutes);
        }
    }

    public FilaEmergencia getBlue() {
        return blue;
    }

    public void setBlue(FilaEmergencia blue) {
        this.blue = blue;
    }

    public FilaEmergencia getGreen() {
        return green;
    }

    public void setGreen(FilaEmergencia green) {
        this.green = green;
    }

    public FilaEmergencia getRed() {
        return red;
    }

    public void setRed(FilaEmergencia red) {
        this.red = red;
    }

    public FilaEmergencia getOrange() {
        return orange;
    }

    public void setOrange(FilaEmergencia orange) {
        this.orange = orange;
    }

    public FilaEmergencia getYellow() {
        return yellow;
    }

    public void setYellow(FilaEmergencia yellow) {
        this.yellow = yellow;
    }

    public TipoEmergencia getEmergency() {
        return emergency;
    }

    public void setEmergency(TipoEmergencia emergency) {
        this.emergency = emergency;
    }


}
