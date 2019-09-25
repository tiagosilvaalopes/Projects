package com.example.hospitalapp.model;

import android.location.Location;

import com.google.gson.annotations.SerializedName;

import java.text.Normalizer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Hospital {

    @SerializedName("Id") private String id;
    @SerializedName("Name") private String name;

    @SerializedName("Longitude")private double longitude;
    @SerializedName("Latitude")private double latitude;
    @SerializedName("Address")private String address;
    @SerializedName("Phone")private int phone;
    @SerializedName("Email")private String email;
    @SerializedName("InstitutionURL")private String InstitutionURL;
    @SerializedName("CheckIn")private boolean checkIn;
    @SerializedName("Way")private boolean way;
    @SerializedName("CheckOut")private boolean checkOut;
    @SerializedName("ShareStandbyTimes") private boolean shareStandByTimes;

    private double distance;
    private Date entryTime;
    private Date exitTime;
    private Date totalTime;

    private List<TimesHospital> times;
    private HashMap<String, Integer> timesMap;

    public Hospital(String name, String id, double longitude, double latitude,
                    String address, int phone, String email, String InstitutionURL, boolean checkIn,
                    boolean way, boolean checkOut, double distance) {

        this.name = name;
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.InstitutionURL = InstitutionURL;
        this.checkIn = checkIn;
        this.way = way;
        this.checkOut = checkOut;
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }


    public double getLatitude() {
        return latitude;
    }


    public String getAddress() {
        return address;
    }


    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInstitutionURL() {
        return InstitutionURL;
    }

    public void setInstitutionURL(String InstitutionURL) {
        this.InstitutionURL = InstitutionURL;
    }

    public boolean getCheckIn(){
        return checkIn;
    }

    public void setCheckIn(boolean checkIn){
        this.checkIn = checkIn;
    }

    public boolean getWay(){
        return way;
    }

    public void setWay(boolean way){
        this.way = way;
    }

    public boolean getCheckOut(){
        return checkOut;
    }

    public void setCheckOut(boolean checkOut){
        this.checkOut = checkOut;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public Date getExitTime() {
        return exitTime;
    }

    public void setExitTime(Date exitTime) {
        this.exitTime = exitTime;
    }

    public Date getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Date totalTime) {
        this.totalTime = totalTime;
    }

    public double getDistance(){
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

   public List<TimesHospital> getTimes(){
        return times;
    }

    public void setTimes(List<TimesHospital> times) {
        this.times = times;
        if(times != null) {
            calcTimes("Geral");
            calcTimes("Pediatrica");
            calcTimes("Obstetrica");
        }
    }

    public boolean hasShareStandByTimes() {
        return shareStandByTimes;
    }

    public void setShareStandByTimes(boolean shareStandByTimes) {
        this.shareStandByTimes = shareStandByTimes;
    }

    public double calculateDistance(Location thisLocation){

        return distance(this.getLatitude(), thisLocation.getLatitude(), this.getLongitude(), thisLocation.getLongitude());
    }

    public static double distance(double lat1, double lat2, double lon1,
                                  double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance)/1000;
    }


    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Hospital) {
            return getId().compareTo( ((Hospital)obj).getId() ) == 0;
        }
        return false;
    }

    private void calcTimes(String emergecyType) {
        if(timesMap == null) {
            timesMap = new HashMap<>();
        }

        int count = 0;
        int somatorio = 0;
        for(TimesHospital th : times) {
            if(Normalizer.normalize(th.getEmergency().getDescription(), Normalizer.Form.NFD).replaceAll("\\p{InCOMBINING_DIACRITICAL_MARKS}+", "").contains(emergecyType)) {
                count++;
                somatorio += th.getAllTimes();
            }
        }

        int media;
        if(count > 0) {
            media = somatorio/count;
        }
        else {
            media = 0;
        }

        timesMap.put(emergecyType, media);
    }

    public String getTimeText(String emergencyType) {
        if(timesMap == null) {
            return "Informação indisponível";
        }

        if(timesMap.containsKey(emergencyType)) {
            int segundos = timesMap.get(emergencyType);
            int minutos = segundos/60;

            if(minutos <= 0){
                return segundos + " segundos";
            }

            return minutos + " minutos ";
        }

        return "Informação indisponível";
    }
}
