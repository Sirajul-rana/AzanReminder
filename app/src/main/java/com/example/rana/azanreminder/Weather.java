package com.example.rana.azanreminder;

import com.google.gson.annotations.SerializedName;

public class Weather{

    @SerializedName("pressure")
    private String pressure;


    @SerializedName("temperature")
    private String temperature;

    public Weather(String pressure, String temperature)
    {
        this.pressure = pressure;
        this.temperature = temperature;
    }

    public String getPressure()
    {
        return this.pressure;
    }
    public void setPressure(String pressure)
    {
        this.pressure = pressure;
    }

    public String getTemperature()
    {
        return this.temperature;
    }
    public void setTemperature(String temperature)
    {
        this.temperature = temperature;
    }

}
