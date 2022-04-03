package com.example.weatherapp.Model;

import com.google.gson.annotations.SerializedName;

public class Weather {
    public static final int DEFAULT_DOUBLE = -1000;

    @SerializedName("max_temp")
    private double maxTemp;
    @SerializedName("min_temp")
    private double minTemp;
    @SerializedName("the_temp")
    private double currentTemp;
    @SerializedName("weather_state_name")
    private String summary;

    /*
    All temperatures are initially initialized to -1000 as default. This is done beacuse
    I want to have a random impossible default temperature value in case the temperature is not found in the Json response.
    Regular choices like 0 or -1 dont't work because temperature can be 0 or -1
     */
    public Weather() {
        maxTemp = Weather.DEFAULT_DOUBLE;
        minTemp = Weather.DEFAULT_DOUBLE;
        currentTemp = Weather.DEFAULT_DOUBLE;
        summary = "";
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public String getSummary() {
        return summary;
    }


}
