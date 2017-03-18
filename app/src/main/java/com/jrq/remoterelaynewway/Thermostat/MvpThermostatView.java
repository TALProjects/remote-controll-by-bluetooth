package com.jrq.remoterelaynewway.Thermostat;

import android.widget.ImageView;

import com.jrq.remoterelaynewway.Database.Model.ThermostatRelay;
import com.jrq.remoterelaynewway.OutputRelay.OutputRelayRecyclerAdapter;

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
