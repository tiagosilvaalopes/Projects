package com.example.hospitalapp.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Visitor extends RealmObject {

    private static final DateFormat FORMAT_TIME = new SimpleDateFormat("dd/HH/yyyy");

    @PrimaryKey @Required
    private String id;

    @Required
    private String name;

    @Required
    private Date entryDate;

    @Required
    private Date exitDate;

    public Visitor() {

    }

    public Visitor(String name, Date entryDate, Date exitDate) {
        this.name = name;
        this.entryDate = entryDate;
        this.exitDate = exitDate;

        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEntryDate() {
        String time = FORMAT_TIME.format(entryDate);
        return time;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getExitDate() {
        String time = FORMAT_TIME.format(exitDate);
        return time;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }

    public String getTotalTime(){
        Date visitTime = new Date( exitDate.getTime() - entryDate.getTime() );
        SimpleDateFormat pattern = new SimpleDateFormat("HH:mm:ss");
        String time = pattern.format(visitTime);

        return time;
    }
}
