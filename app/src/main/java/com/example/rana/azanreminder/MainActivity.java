package com.example.rana.azanreminder;

import android.os.CountDownTimer;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String BASE_URL = "http://muslimsalat.com/dhaka/";
    private static final String API_KEY = "696e7c952f1f62dae8b89d5765ab0ac7";

    private TextView cityLabel;
    private TextView cityName;
    private TextView pressureValue;
    private TextView temperatureValue;
    private TextView fajrTime;
    private TextView johorTime;
    private TextView asorTime;
    private TextView magribTime;
    private TextView ishaTime;

    private TextClock clock;
    private TimePicker picker;
    private TextView timeValue;
    private TextView textView;
    private TextView textViewAlert;
    private Button check;
    AzanModel azanModel;



    private String format = "", city;
    private Calendar calendar;
    private int Hours, Mins;
    long mills;
    CounterClass timer;

    Date date1;

    @Override
    protected void onStart() {
        super.onStart();
        //fajrTime.setText(fajr);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //fajrTime.setText(fajr);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        azanModel = new AzanModel();
        cityName = (TextView)findViewById(R.id.cityName);
        picker = (TimePicker)findViewById(R.id.timePicker);
        pressureValue = (TextView)findViewById(R.id.pressureValue);
        temperatureValue = (TextView)findViewById(R.id.temperatureValue);
        fajrTime = (TextView)findViewById(R.id.fajrTime);
        johorTime = (TextView)findViewById(R.id.johorTime);
        asorTime = (TextView)findViewById(R.id.asorTime);
        magribTime = (TextView)findViewById(R.id.magribTime);
        ishaTime = (TextView)findViewById(R.id.ishaTime);

        clock = (TextClock)findViewById(R.id.textClock);
        picker = (TimePicker)findViewById(R.id.timePicker);
        timeValue = (TextView)findViewById(R.id.textView);
        check = (Button)findViewById(R.id.button);
        calendar = Calendar.getInstance();
        textView = (TextView)findViewById(R.id.textView2);
        textViewAlert = (TextView)findViewById(R.id.textViewFinish);
        textViewAlert.setText("00:00");

        CallLoad(API_KEY);
        check.setOnClickListener(this);

    }

    public void CallLoad(String API_KEY){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<Azan> call = apiService.getAzanReport(API_KEY);
        call.enqueue(new Callback<Azan>() {
            String city, temp, fajr, johor, asor, magrib, isha;
            @Override
            public void onResponse(Call<Azan> call, retrofit2.Response<Azan> response) {
                //Showing city name
                city = response.body().getcity().toString();
                //Showing Weather
                Weather weather = response.body().getToday_weather();
                temp = weather.getTemperature().toString()+(char) 0x00B0;


                //Showing Azan times
                List<AllAzanTime> allAzanTimes = response.body().getAzanTime();
                fajr = allAzanTimes.get(0).getFajr().toString();
                johor = allAzanTimes.get(0).getJohor().toString();
                asor = allAzanTimes.get(0).getAsor().toString();
                magrib = allAzanTimes.get(0).getMaghrib().toString();
                isha = allAzanTimes.get(0).getIsha().toString();
                setCall(city, temp, fajr, johor, asor, magrib, isha);
            }

            @Override
            public void onFailure(Call<Azan> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setCall(String city,String temp,String fajr,String johor,String asor,String magrib,String isha){
        cityName.setText(city);
        azanModel.setCity(city);
        azanModel.setTemperature(temp);
        azanModel.setFajr(fajr);
        azanModel.setJohor(johor);
        azanModel.setAsor(asor);
        azanModel.setMagrib(magrib);
        azanModel.setIsha(isha);
    }

    @Override
    public void onClick(View v) {
        Button b = (Button)v;
        int hour = picker.getCurrentHour();
        int min = picker.getCurrentMinute();
        if (check == b)
        {

            String currentTime = clock.getText().toString();
            String selectedTime = showTime(hour, min);
            textView.setText(selectedTime);
            try
            {

                SimpleDateFormat format = new SimpleDateFormat("hh:mm aa");
                Date Date1 = format.parse(currentTime);
                Date Date2 = format.parse(selectedTime);
                mills = Date1.getTime() - Date2.getTime();
                Log.v("Data1", ""+Date1.getTime());
                Log.v("Data2", ""+Date2.getTime());
                Hours = (int) (mills/(1000 * 60 * 60));
                Mins = (int) (mills/(1000*60)) % 60;

                String diff = Hours + ":" + Mins; // updated value every1 second
                timeValue.setText(Long.toString(mills));
                textViewAlert.setText(null);
                timer = new CounterClass(mills, 1000);
                timer.start();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                timeValue.setText("Null");
            }
        }
    }

    public String showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        }
        else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        String s = (new StringBuilder().append(hour).append(":").append(min)
                .append(" ").append(format)).toString();
        return s;
    }



    public class CounterClass extends CountDownTimer{

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUnitsFinished) {
            long millis = millisUnitsFinished;
            String hm = String.format("Next azan will be in %02d hours and %02d minutes and %02d seconds", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

            textViewAlert.setText(hm);
        }

        @Override
        public void onFinish() {
            textViewAlert.setText("Alarm");
            timer.cancel();
        }
    }
}
