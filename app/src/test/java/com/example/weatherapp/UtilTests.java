package com.example.weatherapp;

import com.example.weatherapp.Util.Util;
import com.example.weatherapp.Model.CombinedWeather;
import com.example.weatherapp.Model.Weather;

import org.junit.Assert;
import org.junit.Test;

public class UtilTests {

    String jsonResponse;

    @Test
    public void test_convertStringToCombinedWeather_EmptyString(){
        jsonResponse = "";
        CombinedWeather weather = Util.convertStringToCombinedWeather(jsonResponse);
        Assert.assertNull(weather);
    }

    @Test
    public void test_convertStringToCombinedWeather_EmptyJson(){
        jsonResponse = "{}";
        CombinedWeather weather = Util.convertStringToCombinedWeather(jsonResponse);
        Assert.assertNotNull(weather);

        weather.aggregateWeather();
        Assert.assertEquals(weather.getCity(), "");
        Assert.assertEquals(weather.getSummary(), "");
        Assert.assertEquals(weather.getCurrentTemp(), Weather.DEFAULT_DOUBLE, Util.DOUBLE_EQUALITY_THRESHOLD);
        Assert.assertEquals(weather.getMaxTemp(), Weather.DEFAULT_DOUBLE, Util.DOUBLE_EQUALITY_THRESHOLD);
        Assert.assertEquals(weather.getMinTemp(), Weather.DEFAULT_DOUBLE, Util.DOUBLE_EQUALITY_THRESHOLD);
    }

    @Test
    public void test_convertStringToCombinedWeather_InvalidJson(){
        jsonResponse = "{\"invalidJson\" : [}";
        CombinedWeather weather = Util.convertStringToCombinedWeather(jsonResponse);
        weather.aggregateWeather();
        Assert.assertEquals(weather.getCity(), "");
        Assert.assertEquals(weather.getSummary(), "");
        Assert.assertEquals(weather.getCurrentTemp(), Weather.DEFAULT_DOUBLE, Util.DOUBLE_EQUALITY_THRESHOLD);
        Assert.assertEquals(weather.getMaxTemp(), Weather.DEFAULT_DOUBLE, Util.DOUBLE_EQUALITY_THRESHOLD);
        Assert.assertEquals(weather.getMinTemp(), Weather.DEFAULT_DOUBLE, Util.DOUBLE_EQUALITY_THRESHOLD);
    }

    @Test
    public void test_convertStringToCombinedWeather_ValidJson(){
        jsonResponse = "{\"consolidated_weather\":[{\"id\":5816181572239360,\"weather_state_name\":" +
                "\"Heavy Rain\",\"max_temp\":5.6,\"the_temp\":5.0},\n" +
                "{\"id\":5645809044422656,\"weather_state_name\":\"Heavy Rain\",\"min_temp\":5.2,\"max_temp\":10.1," +
                "\"the_temp\":12.0}],\"title\":\"Toronto\"}\n";
        CombinedWeather weather = Util.convertStringToCombinedWeather(jsonResponse);
        weather.aggregateWeather();
        Assert.assertEquals(weather.getCity(), "Toronto");
        Assert.assertEquals(weather.getSummary(), "Heavy Rain");
        Assert.assertEquals(weather.getCurrentTemp(), 8.5, Util.DOUBLE_EQUALITY_THRESHOLD);
        Assert.assertEquals(weather.getMaxTemp(), 7.85, Util.DOUBLE_EQUALITY_THRESHOLD);
        Assert.assertEquals(weather.getMinTemp(), 5.2, Util.DOUBLE_EQUALITY_THRESHOLD);
    }

    @Test
    public void test_getTemperatureAsString_ValidTemperature(){
        double temp = 4.56;
        String tempAsString = Util.getTemperatureAsString(temp);
        Assert.assertEquals(tempAsString, "5°git add . && git commit -m \"initial commit\"");
    }
}
