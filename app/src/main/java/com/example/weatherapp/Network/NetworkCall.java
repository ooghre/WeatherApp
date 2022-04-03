package com.example.weatherapp.Network;

import android.content.Context;

import org.chromium.net.CronetEngine;
import org.chromium.net.UrlRequest;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NetworkCall {

    private static UrlRequest request;
    private static  CronetEngine cronetEngine;
    private WeatherURLRequestCallBack requestCallBack;

    public NetworkCall(Context context, WeatherURLRequestCallBack requestCallBack) {
        CronetEngine.Builder myBuilder = new CronetEngine.Builder(context);
        cronetEngine = myBuilder.build();
        this.requestCallBack = requestCallBack;
    }

    public NetworkCall(WeatherURLRequestCallBack requestCallBack) {
        this.requestCallBack = requestCallBack;
    }

    public void get(String requestURL){
        Executor executor = Executors.newSingleThreadExecutor();
        UrlRequest.Builder requestBuilder = cronetEngine.newUrlRequestBuilder( requestURL, requestCallBack, executor);
        request = requestBuilder.build();
        request.start();
    }
}
