package com.jrq.remoterelay.Thermostat.View;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jrq.remoterelay.Database.Model.ThermostatRelay;
import com.jrq.remoterelay.R;
import com.jrq.remoterelay.RemoteApplication;
import com.jrq.remoterelay.Thermostat.MvpThermostatView;
import com.jrq.remoterelay.Thermostat.Presenter.ThermostatPresenter;
import com.jrq.remoterelay.Thermostat.ThermostatRecyclerAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jrq on 2016-09-12.
 */
public class ThermostatFragment extends Fragment implements MvpThermostatView {


    @BindView(R.id.citytext)
    public TextView mCityText;

    @BindView(R.id.mintemptext)
    public TextView mMinTempText;

    @BindView(R.id.maxtemptext)
    public TextView mMaxTempText;

    @BindView(R.id.currenttemptext)
    public TextView mCurrentTempText;

    @BindView(R.id.weathericon)
    public ImageView mWeatherIconImage;

    @BindView(R.id.weatherInfoAlert)
    public TextView weatherAlert;

    @BindView(R.id.weatherInfo)
    public RelativeLayout weatherInfo;

    @BindView(R.id.thermostat_recycler_view)
    public RecyclerView recyclerView;

    @Inject
    ThermostatPresenter thermostatPresenter;

    public static ThermostatRecyclerAdapter thermostatRecyclerAdapter;

    private static List<ThermostatRelay> thermostatRelayList;
    private static boolean isStartContentView = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser == true) {
            thermostatPresenter.registerEventBus(true);
            if(isStartContentView == false) {
                thermostatPresenter.startBluetoothServices();
                isStartContentView = true;
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ((RemoteApplication) this.getActivity().getApplication())
                .getComponent()
                .inject(this);

        thermostatPresenter.attachView(this, getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.thermostat_fragment, container, false);
        ButterKnife.bind(this, v);
        thermostatRelayList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        thermostatPresenter.startLoadingDate();

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isStartContentView == true) {
            SharedPreferences prefs = getContext().getSharedPreferences("userdetails", getContext().MODE_PRIVATE);
            Gson gson = new Gson();
            String json = prefs.getString("thermostatProperties", "");

            Type type = new TypeToken<List<ThermostatRelay>>() {}.getType();
            ArrayList<ThermostatRelay> tDeviceProperties = new ArrayList<ThermostatRelay>();
            tDeviceProperties = gson.fromJson(json, type);
            if (tDeviceProperties != null) {
                refreshListView(tDeviceProperties);
            } else {
                thermostatRelayList.clear();
                refreshListView(thermostatRelayList);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        SharedPreferences prefs = getContext().getSharedPreferences("userdetails", getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        try {
            Gson gson = new Gson();
            String json = gson.toJson(thermostatRelayList);
            editor.putString("thermostatProperties", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        editor.commit();
    }

    @Override
    public void refreshListView(List<ThermostatRelay> outputRelays) {

        if(thermostatRelayList.size() > 0) {
            thermostatRelayList = outputRelays;
            thermostatRecyclerAdapter = (ThermostatRecyclerAdapter) recyclerView.getAdapter();
            thermostatRecyclerAdapter.setThermostatRelays(thermostatRelayList);
            thermostatRecyclerAdapter.notifyDataSetChanged();
        } else {
            thermostatRelayList = outputRelays;
            thermostatRecyclerAdapter = new ThermostatRecyclerAdapter(getActivity(), thermostatPresenter, thermostatRelayList);
            recyclerView.setAdapter(thermostatRecyclerAdapter);
        }
    }

    @Override
    public void setStartContentView() {
        thermostatPresenter.unBindServices();
        isStartContentView = false;
    }

    @Override
    public void setWeatherInfo(String city, String maxTemp, String minTemp, String currentTemp, int IdImageWeather) {
        mCityText.setText(city);
        mMaxTempText.setText(maxTemp);
        mMinTempText.setText(minTemp);
        mCurrentTempText.setText(currentTemp);
        mWeatherIconImage.setImageResource(IdImageWeather);
    }

    @Override
    public void refreshWeatherInfo(boolean enables, boolean gps) {
        if(enables == true) {
            if(gps == false) {
                weatherAlert.setText("Brak włączonych opcji dla gps");
                weatherInfo.setVisibility(View.GONE);
                weatherAlert.setVisibility(View.VISIBLE);
            } else {
                weatherAlert.setVisibility(View.GONE);
                weatherInfo.setVisibility(View.VISIBLE);
            }
        } else {
            weatherInfo.setVisibility(View.GONE);
            weatherAlert.setVisibility(View.VISIBLE);
        }
    }

    public void refreshListView() {
        recyclerView.getAdapter().notifyDataSetChanged();
    }
}
