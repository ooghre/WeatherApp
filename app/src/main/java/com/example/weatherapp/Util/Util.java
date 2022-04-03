package com.example.weatherapp.Util;

import android.util.Log;

import com.example.weatherapp.Model.CombinedWeather;
import com.example.weatherapp.Model.Weather;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class Util {
    private final static String TAG = CombinedWeather.class.getSimpleName();
    public final  static double DOUBLE_EQUALITY_THRESHOLD = 0.0001;

    public static CombinedWeather convertStringToCombinedWeather(String weatherJson){
        Gson gson = new Gson();
        CombinedWeather combinedWeather = new CombinedWeather();
        try{
            combinedWeather = gson.fromJson(weatherJson, CombinedWeather.class);
        }
        catch (JsonSyntaxException | NumberFormatException exception){
            Log.e(TAG, "error parsing json data " + exception.getMessage());
        }

        if (combinedWeather!= null){
            combinedWeather.aggregateWeather();
            System.out.println(combinedWeather);
        }

        return combinedWeather;
    }

    public static boolean doublesIsEqual(double first, double second) {
        return Math.abs(first - second) < DOUBLE_EQUALITY_THRESHOLD;
    }

    public static String getTemperatureAsString(double temp) {
        if (!doublesIsEqual(temp, Weather.DEFAULT_DOUBLE)){
            return String.format("%.0f°", temp);
        }
        return "";
    }
}
