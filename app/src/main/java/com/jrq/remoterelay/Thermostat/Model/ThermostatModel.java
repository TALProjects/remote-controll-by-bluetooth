package com.jrq.remoterelay.Thermostat.Model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.jrq.remoterelay.RemoteApplication;
import com.jrq.remoterelay.Thermostat.Presenter.ThermostatPresenter;
import com.jrq.remoterelay.Thermostat.WeatherInfo.APIManager;
import com.jrq.remoterelay.Thermostat.WeatherInfo.Model.Main;
import com.jrq.remoterelay.Utils.GeoLocationService;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by jrq on 2016-09-20.
 */

public class ThermostatModel {
    private static final String API_KEY = "724099915fe2f37021f6bfb00d8b8b29";
    private static String mCity;

    @Inject
    ThermostatPresenter mThermostatPresenter;

    public ThermostatModel(Context context) {
        ((RemoteApplication) context.getApplicationContext())
                .getComponent()
                .inject(this);
    }

    public void startWeatherInfo(ConnectivityManager connMgr, GeoLocationService geoLocationService) {

        if (geoLocationService.canGetLocation()) {
            Double doubleLatitude = geoLocationService.getLatitude();
            Double doubleLonglatitude = geoLocationService.getLongitude();
            String stringLatitude = doubleLatitude.toString();
            String stringLongitude = doubleLonglatitude.toString();

            NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            boolean isConnected = wifi != null && wifi.isConnectedOrConnecting() ||
                    mobile != null && mobile.isConnectedOrConnecting();

            if (isConnected) {
                mThermostatPresenter.refreshWeatherInfo(true, true);
            } else {
                mThermostatPresenter.refreshWeatherInfo(false, true);
                return;
            }

            mCity = geoLocationService.getCity(doubleLatitude, doubleLonglatitude);

            APIManager.getApiService().getWeatherInfo(stringLatitude,
                    stringLongitude,
                    "10",
                    API_KEY,
                    "metric",
                    callback);

        } else {
          //  geoLocationService.showSettingsAlert();
            mThermostatPresenter.refreshWeatherInfo(false, true);
        }
    }

    private Callback<Main> callback = new Callback<Main>() {
        String icon = null;

        @Override
        public void success(Main response, Response response2) {
            String cityText = "";
            String maxTempText;
            String minTempText;
            String currentTempText;

            Double minTemp = 293.00;
            Double maxTemp = -293.00;

            if (mCity != null && response.getCity() != null) {
                cityText = "Aktualna Pogoda: \nMiasto: " + response.getCity().getName() + " (" + mCity + ")";
            } else if (response.getCity() != null) {
                cityText = "Aktualna Pogoda: \nMiasto: " + response.getCity().getName();
            }

            for (int i = 0; i < response.getList().size(); i++) {
                Double tMinTemp = response.getList().get(i).getMain().getTemp_min();
                if (tMinTemp < minTemp) {
                    minTemp = tMinTemp;
                }

                Double tMaxTemp = response.getList().get(i).getMain().getTemp_max();
                if (tMaxTemp > maxTemp) {
                    maxTemp = tMaxTemp;
                }
            }
            try {
                maxTempText = "Maksymalna Temperatura: " + maxTemp + "\u2103";
                minTempText = "Minimalna Temperatura: " + minTemp + "\u2103";
                currentTempText = "Aktualna Temperatura: " + response.getList().get(0).getMain().getTemp() + "\u2103";

                icon = response.getList().get(0).getWeather().get(0).getIcon();
                mThermostatPresenter.setWeatherInfo(cityText, maxTempText, minTempText, currentTempText, icon);
            } catch (Exception e) {
            }
        }

        @Override
        public void failure(RetrofitError error) {
        }
    };
}
