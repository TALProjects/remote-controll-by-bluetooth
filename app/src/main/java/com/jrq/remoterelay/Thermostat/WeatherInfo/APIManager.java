package com.jrq.remoterelay.Thermostat.WeatherInfo;

import com.jrq.remoterelay.Thermostat.WeatherInfo.Model.Main;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by jrq on 2016-09-19.
 */

public class APIManager {
    private static Mc2Service mc2Service;
    private static final String URL = "http://api.openweathermap.org/data/2.5";


    public interface Mc2Service {

        @GET("/forecast")
        void getWeatherInfo(@Query("lat") String latitude,
                            @Query("lon") String longitude,
                            @Query("cnt") String cnt,
                            @Query("appid") String appid,
                            @Query("units") String units,
                            Callback<Main> cb);
    }

    public static Mc2Service getApiService() {
        RestAdapter restAdapter = new RestAdapter.Builder().
                setEndpoint(URL).setLogLevel(RestAdapter.LogLevel.FULL).build();

        mc2Service = restAdapter.create(Mc2Service.class);

        return mc2Service;
    }
}
