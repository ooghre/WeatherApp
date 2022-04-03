package com.example.weatherapp.Network;

import android.util.Log;

import com.example.weatherapp.Util.Util;
import com.example.weatherapp.Model.WeatherFromRequestDelegate;

import org.chromium.net.CronetException;
import org.chromium.net.UrlRequest;
import org.chromium.net.UrlResponseInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;

public class WeatherURLRequestCallBack extends UrlRequest.Callback {

    private final String TAG = this.getClass().getSimpleName();
    private final ByteArrayOutputStream bytesReceived = new ByteArrayOutputStream();
    private final WritableByteChannel receiveChannel = Channels.newChannel(bytesReceived);


    private String content = "";
    private WeatherFromRequestDelegate delegate;

    public WeatherURLRequestCallBack(WeatherFromRequestDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void onRedirectReceived(UrlRequest request, UrlResponseInfo info, String newLocationUrl) {
        Log.i(TAG, "onRedirectReceived method called.");
        request.followRedirect();
    }

    @Override
    public void onResponseStarted(UrlRequest request, UrlResponseInfo info) {
        Log.i(TAG, "onResponseStarted method called.");
        request.read(ByteBuffer.allocateDirect(65536));

        int httpStatusCode = info.getHttpStatusCode();
        if (httpStatusCode >399) {
            Log.e(TAG, "Request failed with erroneous http code" + httpStatusCode + " "+ info.getHttpStatusText());
            request.cancel();
        }
    }

    @Override
    public void onReadCompleted(UrlRequest request, UrlResponseInfo info, ByteBuffer byteBuffer) {

        Log.i(TAG, "onReadCompleted called" );

        // The byte buffer we're getting in the callback hasn't been flipped for reading,
        // so flip it so we can read the content.
        byteBuffer.flip();

        try {
            receiveChannel.write(byteBuffer);
        } catch (IOException e) {
            android.util.Log.i(TAG, "IOException during ByteBuffer read. Details: ", e);
        }
        // Reset the buffer to prepare it for the next read
        byteBuffer.clear();

        // Continue reading the request
        request.read(byteBuffer);
    }

    @Override
    public void onSucceeded(UrlRequest request, UrlResponseInfo info) {
        Log.i(TAG, "Request succeeded");
        byte[] bodyBytes = bytesReceived.toByteArray();
        content = new String(bodyBytes, Charset.forName("UTF-8"));

        Log.i(TAG, content);
        if (delegate!=null){
            delegate.setWeather(Util.convertStringToCombinedWeather(content));
        }
    }

    @Override
    public void onFailed(UrlRequest request, UrlResponseInfo info, CronetException error) {
        Log.e(TAG, "Request failed", error);
    }

}