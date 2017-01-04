package com.example.rana.azanreminder;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Rana on 25-Sep-16.
 */
public interface ApiInterface {

    @GET("daily.json?api_key=696e7c952f1f62dae8b89d5765ab0ac7")
    Call<Azan> getAzanReport(@Query("api_key") String apiKey);
}
