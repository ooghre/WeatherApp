package com.example.weatherapp.Model;

import android.text.TextUtils;

import com.example.weatherapp.Util.Util;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CombinedWeather {

    @SerializedName("woeid")
    private String id;
    @SerializedName("title")
    private String city;
    private transient double maxTemp;
    private transient double minTemp;
    private transient double currentTemp;
    private transient String summary;
    @SerializedName("consolidated_weather")
    private List<Weather> weatherList;

    public CombinedWeather() {
        city = "";
        weatherList = new ArrayList<Weather>();
        maxTemp = Weather.DEFAULT_DOUBLE;
        minTemp = Weather.DEFAULT_DOUBLE;
        currentTemp = Weather.DEFAULT_DOUBLE;
        summary = "";
    }

    public String getCity() {
        return city;
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

    /*
    * This method minTemp, maxTemp and current temp based on the average in the weather list
    * This method also sets summary based on the most frequent weather summary in weather list
    * */
    public void aggregateWeather(){
        if (weatherList!=null && weatherList.size()!=0) {
            minTemp = averageTemp("minTemp");
            maxTemp = averageTemp("maxTemp");
            currentTemp = averageTemp("currentTemp");
            summary = averageSummary();
        }
    }

    /*
    *  This method returns the average of a particular temperature considering only temperature values
    *  that are not Weather.DEFAULT_DOUBLE
    * */
    private double averageTemp(String method){
        double sum = 0.0;
        int count = 0;      //keep track of number of non 0 temperatures
        for (Weather weather: weatherList) {
            if (method.equals("minTemp") && !Util.doublesIsEqual(weather.getMinTemp(), Weather.DEFAULT_DOUBLE)){
                sum+= weather.getMinTemp();
                count++;
            }
            else if (method.equals("maxTemp") && !Util.doublesIsEqual(weather.getMaxTemp(), Weather.DEFAULT_DOUBLE)){
                sum+= weather.getMaxTemp();
                count++;
            }
            else if (method.equals("currentTemp") && !Util.doublesIsEqual(weather.getCurrentTemp(), Weather.DEFAULT_DOUBLE)){
                sum+= weather.getCurrentTemp();
                count++;
            }
        }
        if (count==0){
            //if count is 0
            // then we never had any non default values for the temperatures return average temp as Weather.DEFAULT_DOUBLE
            return Weather.DEFAULT_DOUBLE;
        }
        return sum/count;
    }


    /*
    * This method returns the weather summary with the highest occurrence
    * If multiple weather summaries occur the sme amount of times, it returns the first one
    * */
    private String averageSummary(){
        Map<String, Integer> map = new HashMap<String, Integer>();
        int max = 0;
        String value = "";
        for (Weather weather: weatherList) {
            String key = weather.getSummary();
            if (!TextUtils.isEmpty(key) && map.containsKey(key)){
                map.put(key, map.get(key) + 1);
            }
            else{
                map.put(weather.getSummary(), 1);
            }
        }

        for (Map.Entry<String, Integer> element: map.entrySet()) {
            if (element.getValue() > max){
                max = element.getValue();
                value = element.getKey();
            }
        }
        return value;
    }

    @Override
    public String toString() {
        return String.format("CombinedWeather{id='%s', city='%s', maxTemp= %.0f, minTemp=%.0f, currentTemp=%.0f, summary='%s'}",
                id, city, maxTemp, minTemp, currentTemp, summary);
    }
}
