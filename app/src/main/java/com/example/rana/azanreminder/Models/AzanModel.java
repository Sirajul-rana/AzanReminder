package com.example.rana.azanreminder.Models;

/**
 * Created by siraj on 01-Jan-17.
 */

public class AzanModel {
    public String getAsor() {
        return asor;
    }

    public void setAsor(String asor) {
        this.asor = asor;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFajr() {
        return fajr;
    }

    public void setFajr(String fajr) {
        this.fajr = fajr;
    }

    public String getIsha() {
        return isha;
    }

    public void setIsha(String isha) {
        this.isha = isha;
    }

    public String getJohor() {
        return johor;
    }

    public void setJohor(String johor) {
        this.johor = johor;
    }

    public String getMagrib() {
        return magrib;
    }

    public void setMagrib(String magrib) {
        this.magrib = magrib;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    private String city;
    private String temperature;
    private String fajr;
    private String johor;
    private String asor;
    private String magrib;
    private String isha;
}
