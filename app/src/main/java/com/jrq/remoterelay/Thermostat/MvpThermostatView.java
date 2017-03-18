package com.jrq.remoterelay.Thermostat;

import com.jrq.remoterelay.Database.Model.ThermostatRelay;

import java.util.List;

/**
 * Created by jrq on 2016-09-18.
 */

public interface MvpThermostatView {
    public void refreshListView();
    public void refreshWeatherInfo(boolean enabled, boolean gps);
    public void setWeatherInfo(String city, String maxTemp, String minTemp, String currentTemp, int IdImageWeather);
    public void refreshListView(List<ThermostatRelay> outputRelays);
    public void setStartContentView();
}
