package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.example.weatherapp.Network.WeatherURLRequestCallBack;
import com.example.weatherapp.Network.NetworkCall;
import com.example.weatherapp.Util.Util;
import com.example.weatherapp.Model.CombinedWeather;
import com.example.weatherapp.Model.WeatherFromRequestDelegate;

public class MainActivity extends AppCompatActivity implements WeatherFromRequestDelegate {

    private CombinedWeather weather;
    private final String LOCATION_ID = "4118";
    private final String requestURL = "https://www.metaweather.com/api/location/" + LOCATION_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WeatherURLRequestCallBack requestCallBack = new WeatherURLRequestCallBack(this);
        NetworkCall networkCall = new NetworkCall(this, requestCallBack);
        networkCall.get(requestURL);
    }

    @Override
    public void setWeather(CombinedWeather weather) {
        this.weather = weather;

        TextView city = (TextView)findViewById(R.id.city);
        TextView minTemp = (TextView)findViewById(R.id.min_temperature);
        TextView maxTemp = (TextView)findViewById(R.id.max_temperature);
        TextView currentTemp = (TextView)findViewById(R.id.current_temperature);
        TextView tempSummary = (TextView)findViewById(R.id.temperature_summary);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!TextUtils.isEmpty(weather.getCity())){
                    city.setText(weather.getCity());
                    minTemp.setText( "L: " + Util.getTemperatureAsString(weather.getMinTemp()));
                    maxTemp.setText("H: " + Util.getTemperatureAsString(weather.getMaxTemp()));
                    currentTemp.setText(Util.getTemperatureAsString(weather.getCurrentTemp()));
                    tempSummary.setText(weather.getSummary());
                }
            }
        });
    }
}