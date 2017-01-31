package com.example.rana.azanreminder.Interface;

import com.example.rana.azanreminder.Models.Azan;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Rana on 25-Sep-16.
 */
public interface ApiInterface {

    @GET("daily.json")
    Call<Azan> getAzanReport(@Query("api_key") String apiKey);
}
