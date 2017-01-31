package com.example.rana.azanreminder.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.rana.azanreminder.ApiClient;
import com.example.rana.azanreminder.Interface.ApiInterface;
import com.example.rana.azanreminder.Models.Azan;
import com.example.rana.azanreminder.Models.AzanModel;
import com.example.rana.azanreminder.R;
import com.example.rana.azanreminder.UtilitiesClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.xml.datatype.Duration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String BASE_URL = "http://muslimsalat.com/dhaka/";
    private static final String API_KEY = "696e7c952f1f62dae8b89d5765ab0ac7";
    private static final String MyPREFERENCES = "MyPref";

    private TextView timeLeftFajr;
    private TextView timeLeftJohor;
    private TextView timeLeftAsor;
    private TextView timeLeftMagrib;
    private TextView timeLeftIsha;
    private TextView cityName;
    private TextView pressureValue;
    private TextView temperatureValue;
    private TextView fajrTime;
    private TextView johorTime;
    private TextView asorTime;
    private TextView magribTime;
    private TextView ishaTime;

    private TextClock clock;
    private Button button;
    AzanModel azanModel;
    private UtilitiesClass utility;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        azanModel = new AzanModel();
        utility = new UtilitiesClass();
        cityName = (TextView)findViewById(R.id.cityName);
        pressureValue = (TextView)findViewById(R.id.pressureValue);
        temperatureValue = (TextView)findViewById(R.id.temperatureValue);
        fajrTime = (TextView)findViewById(R.id.fajrTime);
        johorTime = (TextView)findViewById(R.id.johorTime);
        asorTime = (TextView)findViewById(R.id.asorTime);
        magribTime = (TextView)findViewById(R.id.magribTime);
        ishaTime = (TextView)findViewById(R.id.ishaTime);
        timeLeftFajr = (TextView)findViewById(R.id.timeleftfajr);
        timeLeftJohor = (TextView)findViewById(R.id.timeleftjohor);
        timeLeftAsor = (TextView)findViewById(R.id.timeleftasor);
        timeLeftMagrib = (TextView)findViewById(R.id.timeleftmagrib);
        timeLeftIsha = (TextView)findViewById(R.id.timeleftisha);
        preferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        clock = (TextClock)findViewById(R.id.textClock);
        button = (Button) findViewById(R.id.button);
        if (utility.isInternetAvailable())
        {

            preferences.edit().remove(MainActivity.MyPREFERENCES).commit();
            CallLoad(API_KEY);

        }
        else
        {
            loadFromPreferense();
        }
        button.setOnClickListener(this);

    }

    public void CallLoad(String API_KEY){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Azan> call = apiService.getAzanReport(API_KEY);
        call.enqueue(new Callback<Azan>() {
            @Override
            public void onResponse(Call<Azan> call, Response<Azan> response) {
                //Showing city name
                int statuscode = response.code();
                if (response.isSuccessful())
                {
                    Log.d("LoginActivity", "onResponse: "+ response.body().getcity());
                    Azan azan = response.body();
                    setPreferences(azan);
                    loadFromPreferense();
                }
                else
                {
                    Log.d("LoginActivity", "onResponse: "+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<Azan> call, Throwable t) {
                Log.d("LoginActivity", "onFailure: "+ t.getMessage());
            }
        });

    }

    public void setPreferences(Azan azan){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("city", azan.getcity());
        editor.putString("pressure", azan.getToday_weather().getPressure());
        editor.putString("temperature", azan.getToday_weather().getTemperature()+(char) 0x00B0);
        editor.putString("fajr", azan.getAzanTime().get(0).getFajr());
        editor.putString("johor", azan.getAzanTime().get(0).getJohor());
        editor.putString("asor", azan.getAzanTime().get(0).getAsor());
        editor.putString("magrib", azan.getAzanTime().get(0).getMaghrib());
        editor.putString("isha", azan.getAzanTime().get(0).getIsha());
        editor.commit();
    }

    public void loadFromPreferense(){
        cityName.setText(preferences.getString("city", ""));
        pressureValue.setText(preferences.getString("pressure", ""));
        temperatureValue.setText(preferences.getString("temperature", ""));
        fajrTime.setText(preferences.getString("fajr", ""));
        johorTime.setText(preferences.getString("johor", ""));
        asorTime.setText(preferences.getString("asor", ""));
        magribTime.setText(preferences.getString("magrib", ""));
        ishaTime.setText(preferences.getString("isha", ""));
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.button:
            {
                try
                {
                    SimpleDateFormat format = new SimpleDateFormat("hh:mm aa");
                    Date Date1 = format.parse(clock.getText().toString());
                    Date fajr = format.parse(preferences.getString("fajr", ""));
                    Date johor = format.parse(preferences.getString("johor", ""));
                    Date asor = format.parse(preferences.getString("asor", ""));
                    Date magrib = format.parse(preferences.getString("magrib", ""));
                    Date isha = format.parse(preferences.getString("isha", ""));
                    setFajr(Date1, fajr);
                    setJohor(Date1, johor);
                    setAsor(Date1, asor);
                    setMagrib(Date1, magrib);
                    setIsha(Date1, isha);
                }
                catch (ParseException e)
                {
                    e.printStackTrace();
                }
                /*try
                {

                    SimpleDateFormat format = new SimpleDateFormat("hh:mm aa");
                    setFajr();
                    Date Date1 = format.parse(clock.getText().toString());
                    Date Date2 = format.parse(timeValue);
                    long mills = Date1.getTime() - Date2.getTime();
                    if (Date2.getTime() > Date1.getTime())
                    {
                        finalMillis = mills;
                        Hours = (int) (finalMillis/(1000 * 60 * 60));
                        Mins = (int) (finalMillis/(1000*60)) % 60;
                    }
                    else
                    {
                        finalMillis = 86400000 - mills;
                        Hours = (int) (finalMillis/(1000 * 60 * 60));
                        Mins = (int) (finalMillis/(1000*60)) % 60;
                    }
                    //finalMillis = 86400000 - mills;
                    Log.v("Data1", ""+Date1.getTime());
                    Log.v("Data2", ""+Date2.getTime());


                    String diff = abs(Hours) + ":" + abs(Mins); // updated value every1 second
                    timeLeft.setText(diff);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }*/

            }
        }
    }

    public void setFajr(Date current, Date azan)
    {
        long finalMillis=0;
        int Hours = 0;
        int Mins = 0;
        try
        {

            SimpleDateFormat format = new SimpleDateFormat("hh:mm aa");
            long mills = current.getTime() - azan.getTime();
            if (azan.getTime() > current.getTime())
            {
                finalMillis = mills;
                Hours = (int) (finalMillis/(1000 * 60 * 60));
                Mins = (int) (finalMillis/(1000*60)) % 60;
            }
            else
            {
                finalMillis = 86400000 - mills;
                Hours = (int) (finalMillis/(1000 * 60 * 60));
                Mins = (int) (finalMillis/(1000*60)) % 60;
            }
            //finalMillis = 86400000 - mills;
            Log.v("Data1", ""+current.getTime());
            Log.v("Data2", ""+azan.getTime());


            String diff = "Time left for fajr azan"+abs(Hours) + "hours and" + abs(Mins)+" minutes"; // updated value every1 second
            timeLeftFajr.setText(diff);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setJohor(Date current, Date azan)
    {
        long finalMillis=0;
        int Hours = 0;
        int Mins = 0;
        try
        {

            SimpleDateFormat format = new SimpleDateFormat("hh:mm aa");
            long mills = current.getTime() - azan.getTime();
            if (azan.getTime() > current.getTime())
            {
                finalMillis = mills;
                Hours = (int) (finalMillis/(1000 * 60 * 60));
                Mins = (int) (finalMillis/(1000*60)) % 60;
            }
            else
            {
                finalMillis = 86400000 - mills;
                Hours = (int) (finalMillis/(1000 * 60 * 60));
                Mins = (int) (finalMillis/(1000*60)) % 60;
            }
            //finalMillis = 86400000 - mills;
            Log.v("Data1", ""+current.getTime());
            Log.v("Data2", ""+azan.getTime());


            String diff = "Time left for johor azan"+abs(Hours) + "hours and" + abs(Mins)+" minutes"; // updated value every1 second
            timeLeftJohor.setText(diff);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setAsor(Date current, Date azan)
    {
        long finalMillis=0;
        int Hours = 0;
        int Mins = 0;
        try
        {

            SimpleDateFormat format = new SimpleDateFormat("hh:mm aa");
            long mills = current.getTime() - azan.getTime();
            if (azan.getTime() > current.getTime())
            {
                finalMillis = mills;
                Hours = (int) (finalMillis/(1000 * 60 * 60));
                Mins = (int) (finalMillis/(1000*60)) % 60;
            }
            else
            {
                finalMillis = 86400000 - mills;
                Hours = (int) (finalMillis/(1000 * 60 * 60));
                Mins = (int) (finalMillis/(1000*60)) % 60;
            }
            //finalMillis = 86400000 - mills;
            Log.v("Data1", ""+current.getTime());
            Log.v("Data2", ""+azan.getTime());


            String diff = "Time left for asor azan"+abs(Hours) + "hours and" + abs(Mins)+" minutes"; // updated value every1 second
            timeLeftAsor.setText(diff);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setMagrib(Date current, Date azan)
    {
        long finalMillis=0;
        int Hours = 0;
        int Mins = 0;
        try
        {

            SimpleDateFormat format = new SimpleDateFormat("hh:mm aa");
            long mills = current.getTime() - azan.getTime();
            if (azan.getTime() > current.getTime())
            {
                finalMillis = mills;
                Hours = (int) (finalMillis/(1000 * 60 * 60));
                Mins = (int) (finalMillis/(1000*60)) % 60;
            }
            else
            {
                finalMillis = 86400000 - mills;
                Hours = (int) (finalMillis/(1000 * 60 * 60));
                Mins = (int) (finalMillis/(1000*60)) % 60;
            }
            //finalMillis = 86400000 - mills;
            Log.v("Data1", ""+current.getTime());
            Log.v("Data2", ""+azan.getTime());


            String diff = "Time left for magrib azan"+abs(Hours) + "hours and" + abs(Mins)+" minutes"; // updated value every1 second
            timeLeftMagrib.setText(diff);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void setIsha(Date current, Date azan)
    {
        long finalMillis=0;
        int Hours = 0;
        int Mins = 0;
        try
        {

            SimpleDateFormat format = new SimpleDateFormat("hh:mm aa");
            long mills = current.getTime() - azan.getTime();
            if (azan.getTime() > current.getTime())
            {
                finalMillis = mills;
                Hours = (int) (finalMillis/(1000 * 60 * 60));
                Mins = (int) (finalMillis/(1000*60)) % 60;
            }
            else
            {
                finalMillis = 86400000 - mills;
                Hours = (int) (finalMillis/(1000 * 60 * 60));
                Mins = (int) (finalMillis/(1000*60)) % 60;
            }
            //finalMillis = 86400000 - mills;
            Log.v("Data1", ""+current.getTime());
            Log.v("Data2", ""+azan.getTime());


            String diff = "Time left for isha azan"+abs(Hours) + "hours and" + abs(Mins)+" minutes"; // updated value every1 second
            timeLeftIsha.setText(diff);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
}
