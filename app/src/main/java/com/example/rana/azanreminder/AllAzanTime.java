package com.example.rana.azanreminder;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Rana on 26-Sep-16.
 */
public class AllAzanTime {
    @SerializedName("fajr")
    private String fajr;

    @SerializedName("dhuhr")
    private String johor;

    @SerializedName("asr")
    private String asor;

    @SerializedName("maghrib")
    private String maghrib;

    @SerializedName("isha")
    private String isha;

    public String getFajr()
    {
        return this.fajr;
    }
    public void setFajr(String fajr)
    {
        this.fajr = fajr;
    }

    public String getJohor()
    {
        return this.johor;
    }
    public void setJohor(String johor)
    {
        this.johor = johor;
    }

    public String getAsor()
    {
        return this.asor;
    }
    public void setAsor(String asor)
    {
        this.asor = asor;
    }

    public String getMaghrib()
    {
        return this.maghrib;
    }
    public void setMaghrib(String maghrib)
    {
        this.maghrib = maghrib;
    }

    public String getIsha()
    {
        return this.isha;
    }
    public void setIsha(String isha)
    {
        this.isha = isha;
    }
}
