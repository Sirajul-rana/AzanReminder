package com.example.rana.azanreminder.Models;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Rana on 25-Sep-16.
 */
public class Azan {

    @SerializedName("city")
    private String city;

    @SerializedName("today_weather")
    Weather today_weather;

    @SerializedName("items")
    private List<AllAzanTime> azanTime;

    public String getcity()
    {
        return this.city;
    }
    public void setcity(String city)
    {
        this.city = city;
    }

    public Weather getToday_weather() {
        return today_weather;
    }

    public void setWeather(Weather weather) {
        this.today_weather = weather;
    }

    public List<AllAzanTime> getAzanTime() {
        return azanTime;
    }

    public void setWeather(List<AllAzanTime>     azanTime) {
        this.azanTime = azanTime;
    }
}

